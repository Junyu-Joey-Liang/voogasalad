package voogasalad.authoring.controller.mapbuilder;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import voogasalad.authoring.controller.Viewable;
import voogasalad.authoring.util.VisualizationUtil;
import voogasalad.authoring.util.elementbuilders.InputIntegerSliderElement;
import voogasalad.authoring.util.elementbuilders.InputTextElement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class MapPropertiesPane implements Viewable {
    public static final double INT_TOLERANCE = 0.01;
    public static final int PADDING_AMOUNT = 5;
    public static final int MIN_SIZE = 1;
    public static final int MAX_SIZE = 35;
    public static final String RESOURCES_FOLDER = "voogasalad.authoring.controller.mapbuilder.resources.";
    public static final String RESOURCE_PATH = "MapPropertiesResources";
    public static final String NAME_LABEL = "nameLabel";
    public static final String ROW_LABEL = "rowLabel";
    public static final String COL_LABEL = "colLabel";
    public static final String STORE_LABEL = "storeLabel";
    public static final String ID_MAP_NAME_FIELD = "MapNameField";
    public static final String ID_MAP_ROW_SLIDER = "MapRowSlider";
    public static final String ID_MAP_STORE_BUTTON = "MapStoreButton";

    private Set<PropertiesObserver> propertiesObservers;
    private Set<StoreObserver> storeObservers;
    private String name;
    private VBox propertiesPane;

    private InputTextElement nameTextField;
    private InputIntegerSliderElement rowSlider;
    private InputIntegerSliderElement colSlider;
    private Button saveButton;

    private ResourceBundle myResources;

    public MapPropertiesPane(String defaultName, int defaultRows, int defaultCols) {
        myResources = ResourceBundle.getBundle(RESOURCES_FOLDER + RESOURCE_PATH);
        name = defaultName;
        propertiesObservers = new HashSet<>();
        storeObservers = new HashSet<>();
        propertiesPane = new VBox();
        propertiesPane.setPadding(new Insets(PADDING_AMOUNT));

        initNameTextField(defaultName);
        initInputElements(defaultRows, defaultCols);

        propertiesPane.getChildren().addAll(nameTextField.getNode(), rowSlider.getNode(), colSlider.getNode(), saveButton);
    }

    public void updatePane(String name, int rows, int cols) {
        nameTextField.setText(name);
        rowSlider.setVal(rows);
        colSlider.setVal(cols);
    }

    public void addPropertiesObserver(PropertiesObserver observer) {
        propertiesObservers.add(observer);
    }

    public void addStoreObservers(StoreObserver... observers) {
        storeObservers.addAll(Arrays.asList(observers));
    }

    private void initNameTextField(String defaultName){
        nameTextField = new InputTextElement(myResources.getString(NAME_LABEL), defaultName);
        nameTextField.getTextField().setId(ID_MAP_NAME_FIELD);
        nameTextField.getTextField().textProperty().addListener((obs, old, neww) -> setName(neww));
    }

    private void initInputElements(int defaultRows, int defaultCols){
        rowSlider = new InputIntegerSliderElement(myResources.getString(ROW_LABEL), MIN_SIZE, MAX_SIZE, defaultRows, (ob, old, neww) -> updateRows((double) neww));
        rowSlider.setId(ID_MAP_ROW_SLIDER);

        colSlider = new InputIntegerSliderElement(myResources.getString(COL_LABEL), MIN_SIZE, MAX_SIZE, defaultCols, (ob, old, neww) -> updateCols((double) neww));

        saveButton = VisualizationUtil.makeButton(myResources.getString(STORE_LABEL), e -> storeMap());
        saveButton.setId(ID_MAP_STORE_BUTTON);
    }

    private void updateRows(double rows) {
        propertiesObservers.forEach(observer -> observer.updateRows((int) rows));
    }

    private void updateCols(double cols) {
        propertiesObservers.forEach(observer -> observer.updateCols((int) cols));
    }

    private void storeMap() {
        storeObservers.forEach(observer -> observer.updateStore(name));
    }

    @Override
    public Node getNode() {
        return propertiesPane;
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) {
        this.name = name;
    }

}

