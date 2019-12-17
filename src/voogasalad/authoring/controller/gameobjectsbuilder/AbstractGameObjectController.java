package voogasalad.authoring.controller.gameobjectsbuilder;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.controller.Controller;
import voogasalad.authoring.model.Model;
import voogasalad.authoring.util.StorageUtil;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.VisualizationUtil;
import voogasalad.authoring.util.elementbuilders.AuthoringHorizontalScroll;
import voogasalad.authoring.util.elementbuilders.AuthoringIcon;

import java.io.File;
import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.Arrays;
import java.util.Optional;


import static voogasalad.authoring.util.VisConstant.*;

/**
 * abstract controller class for obstacle, projectile, attacker, and defender
 */
public abstract class AbstractGameObjectController implements Controller,GameObjectController {
    private static final ResourceBundle rb = VisConstant.gameObjectBundle;
    private AbstractFeature currentObj;
    private Model model;
    private BorderPane pane;
    private HBox titlePane;
    private AbstractAttributePaneManager centerPaneManager;
    private VBox leftCol;
    private HBox bottomRow;
    private AuthoringHorizontalScroll objectScroll;
    private AuthoringIcon currentEditObjectIcon;
    private int serialNum = 0;
    private Set<Object> observers = new HashSet<>();

    /**
     * Contructor, initializes the panes
     *
     * @param model
     */
    public AbstractGameObjectController(Model model) {
        createPanes();
        initTop();
        initBottom();
        this.model = model;
    }

    @Override
    public void addObserver(Object observer) {
        observers.add(observer);
    }

    @Override
    public String getName() {
        return getType().toUpperCase();
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public void load() {
        updateBottomRow(getType());
        updateObservers();
    }

    /**
     * get observers for this controller
     *
     * @return list of observers
     */
    public Set<Object> getObservers() {
        return observers;
    }

    /**
     * call update on the observers observing this controller
     */
    public abstract void updateObservers();

    /**
     * get the type of this controller
     *
     * @return type of this controller
     */
    public abstract String getType();

    /**
     * create the central pane manager for this controller tab
     * @return central pane manager for this controller tab
     */
    public abstract AbstractAttributePaneManager createCentralPane();

    protected AbstractAttributePaneManager getCenterPaneManager() {
        return centerPaneManager;
    }

    Set<AuthoringFeature> getAuthoringFeatures(String type) {
        return model.getAuthoringFeatures(type);
    }

    AuthoringFeature getAuthoringFeature(int ID) {
        return model.getAuthoringFeature(ID);
    }

    private void initMiddle() {
        centerPaneManager.initPane();
    }

    private void selectImage(ActionEvent e) {
        File file = StorageUtil.getFilePrompt(e);
        if (file != null) {
            try {
                String filePath = VisualizationUtil.copyImageAndReturnNewFilePath(file, GAMEOBJECT_IMAGES_FOLDER);
                Image img = new Image(file.toURI().toURL().toString());
                getCurrentObj().getView().setImage(VisualizationUtil.initImageView(filePath).getImage());
                getCurrentObj().setImagePath(filePath);
                currentEditObjectIcon.getIconView().setImage(img);
                updateBottomRow(getType());
            } catch (MalformedURLException ex) {
                VisualizationUtil.showAlert("Incorrect file input!");
            }
        }
    }

    public abstract AbstractFeature createNewObj();

    public abstract String getDefaultName();


    void addObject() {
        boolean flag = getCurrentObj() == null;
        if (!flag){
            if (getAuthoringFeature(currentObj.getID())==null || !currentObj.compare((AbstractFeature) getAuthoringFeature(currentObj.getID()))){
                Alert alert =
                        new Alert(Alert.AlertType.WARNING,
                                rb.getString("AddButtonWarningContent"),
                                ButtonType.YES,
                                ButtonType.NO);
                alert.setHeaderText(rb.getString("AddButtonWarningTitle"));
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES){
                    saveCurrentObj();
                }
            }
            this.currentObj = createNewObj();
        }else {
            this.currentObj = createNewObj();
            initLeft();
            initMiddle();
        }
        setCurrentEditObjectIcon();
        changeName(getDefaultName() + serialNum);
        serialNum++;
        updateBottomRow(getType());
        centerPaneManager.updateAttributePane();
    }


    void saveCurrentObj() {
        if (currentObj != null) {
            if (currentObj.getID() != -1) { //current object have been added to model before, inherent old id
                model.replaceAuthoringFeature(getType(), currentObj.getID(), currentObj);
            } else { //current object not in model, get new id
                model.addAuthoringFeature(getType(), currentObj);
            }
            AbstractFeature cur = (AbstractFeature) model.getAuthoringFeature(currentObj.getID());
            currentObj = cur.deepCopy();
            updateBottomRow(getType());
            updateObservers();
        }
    }

    void changeName(String newName) {
        getCurrentObj().setName(newName);
        currentEditObjectIcon.getNameText().setText(newName);
    }

    protected void setTitle(String title) {
        Text titleText = (Text) this.titlePane.getChildren().get(0);
        titleText.setText(title);
    }


    AbstractFeature getCurrentObj() {
        return currentObj;
    }

    private void updateBottomRow(String type) {
        objectScroll.getRow().getChildren().clear();
        if (model.getAuthoringFeatures(type) != null) {
            for (AuthoringFeature authoringFeature : model.getAuthoringFeatures(type)) {
                AbstractFeature obj = (AbstractFeature) authoringFeature;
                AuthoringIcon icon = VisualizationUtil.createIcon(obj.getID(), obj.getView(), obj.getName(), SMALL_ICON, SMALL_ICON);
                icon.getIconView().setOnMouseClicked(event -> changeCurrentObj(obj.getID()));
                objectScroll.getRow().getChildren().add(icon);
            }
        }
    }

    private void changeCurrentObj(int ID) {
        AbstractFeature abstractFeature = (AbstractFeature) model.getAuthoringFeature(ID);
        currentObj = abstractFeature.deepCopy();
        setCurrentEditObjectIcon();
        this.centerPaneManager.updateAttributePane();
    }

    private void setCurrentEditObjectIcon() {
        if(currentEditObjectIcon==null){
           initLeft();
        }
        this.currentEditObjectIcon.getNameText().setText(currentObj.getName());
        this.currentEditObjectIcon.getIconView().setImage(currentObj.getView().getImage());
    }

    private void initTop() {
        Text text = new Text();
        text.setFont(new Font(TITLE_SIZE));
        titlePane.getChildren().add(text);
    }

    private void initBottom() {
        objectScroll = new AuthoringHorizontalScroll();
        bottomRow.getChildren().add(objectScroll.getScrollPane());
    }


    private void initLeft() {
        currentEditObjectIcon = VisualizationUtil.createIcon(currentObj.getID(), currentObj.getImagePath(), currentObj.getName(), LARGE_ICON, LARGE_ICON);
        leftCol.getChildren().add(currentEditObjectIcon);

        Button chooseImageButton = VisualizationUtil.makeButton("Select Image", e -> selectImage(e));
        leftCol.getChildren().add(chooseImageButton);
    }

    private void createPanes() {
        pane = new BorderPane();
        titlePane = VisualizationUtil.createHBox(Arrays.asList(rb.getString(getType() + "titlePane").split(",")).iterator());
        pane.setTop(titlePane);

        centerPaneManager = createCentralPane();
        pane.setCenter(centerPaneManager.getAttributePane());

        leftCol = VisualizationUtil.createVBox(Arrays.asList(rb.getString(getType() + "leftCol").split(",")).iterator());
        pane.setLeft(leftCol);

        bottomRow = VisualizationUtil.createHBox(Arrays.asList(rb.getString(getType() + "bottomRow").split(",")).iterator());
        pane.setBottom(bottomRow);

        GameObjectControllerRightPane rightPane = new GameObjectControllerRightPane(this);
        pane.setRight(rightPane.getPane());

    }


}
