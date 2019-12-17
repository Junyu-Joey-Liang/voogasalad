package voogasalad.authoring.controller.mapbuilder;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.Pair;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;
import voogasalad.authoring.util.VisualizationUtil;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class MapElement implements Comparable<MapElement> {
    public static final String MAP_IMAGES_FOLDER = "\\src\\voogasalad\\authoring\\resources\\mapimages\\";
    public static final String RELATIVE_PATH_SOURCE = "\\src\\voogasalad\\";
    private static final Background BACKGROUND_SELECTED = new Background(new BackgroundFill(Color.GREENYELLOW, CornerRadii.EMPTY, Insets.EMPTY));
    private static final Background BACKGROUND_UNSELECTED = new Background(new BackgroundFill(Color.LIGHTGREY, CornerRadii.EMPTY, Insets.EMPTY));
    private static final int IMAGE_SIZE = 100;
    private static final int PADDING_AMOUNT = 5;
    private static final String USER_DIR = "user.dir";
    private static final FileChooser FILE_CHOOSER = makeChooser();
    private static final String RESOURCES_FOLDER = "voogasalad.authoring.controller.mapbuilder.resources.";
    private static final String MAP_ELEMENT_RESOURCE_FILE = "MapElementResources";
    private static final String ERROR_LOAD_IMAGE = "errorImageLoad";
    private static final String ERROR_IMAGE = "errorImage";
    private static final String BUTTON_KEY = "buttonlabel";
    private static final String FORWARD_OR_BACKWARD_SLASH = "[\\\\/]";
    private ResourceBundle myResources;
    private Set<MapElementObserver> observers;
    private VBox mainNode;
    private ImageView imageView;
    private String imagePath;
    private MapElementType elementType;

    public MapElement(MapElementType elementType, String imagePath) {
        this.myResources = ResourceBundle.getBundle(RESOURCES_FOLDER + MAP_ELEMENT_RESOURCE_FILE);
        this.observers = new HashSet<>();
        this.mainNode = new VBox();
        this.elementType = elementType;

        Label label = new Label(elementType.getDisplayName());
        initImageView(imagePath);
        Button imageChangeButton = VisualizationUtil.makeButton(myResources.getString(BUTTON_KEY), e -> userChangeImage());

        initMainNode(label, imageView, imageChangeButton);
    }

    private static FileChooser makeChooser() {
        FileChooser result = new FileChooser();
        result.setInitialDirectory(new File(System.getProperty(USER_DIR)));
        return result;
    }

    public void addObserver(MapElementObserver observer) {
        observers.add(observer);
    }

    public Node getNode() {
        return mainNode;
    }

    public Pair<MapElementType, String> getProperties() {
        return new Pair(this.elementType, this.imagePath);
    }

    public void selected() {
        mainNode.setBackground(BACKGROUND_SELECTED);
    }

    public void unselected() {
        mainNode.setBackground(BACKGROUND_UNSELECTED);
    }

    @Override
    public int compareTo(MapElement o) {
        return this.elementType.compareTo(o.getProperties().getKey());
    }

    private void userChangeImage() {
        File imageFile = FILE_CHOOSER.showOpenDialog(mainNode.getScene().getWindow());
        try {
            String actualFileName = imageFile.getPath().split(FORWARD_OR_BACKWARD_SLASH)[imageFile.getPath().split(FORWARD_OR_BACKWARD_SLASH).length - 1];
            String imagePathString = System.getProperty(USER_DIR) + MAP_IMAGES_FOLDER + actualFileName;
            try {
                Files.copy(imageFile.toPath(), Paths.get(imagePathString));
            } catch (FileAlreadyExistsException e) {
                //Intentionally Do Nothing.
            }
            Image image = new Image(String.valueOf(Paths.get(imagePathString).toUri()));
            imageView.setImage(image);
            imagePath = imagePathString.substring(imagePathString.indexOf(RELATIVE_PATH_SOURCE));
            observers.forEach(observer -> observer.updateImageChange(this.elementType, image));
        } catch (Exception e) {
            // Error Handling Complete
            new Alert(Alert.AlertType.ERROR, myResources.getString(ERROR_LOAD_IMAGE)).showAndWait();
        }
    }

    public void changeImage(String imagePath) {
        imagePath = imagePath.replace("\\", "/");
        this.imagePath = imagePath;
        Image image;
        try {
            image = new Image(new FileInputStream(System.getProperty(USER_DIR) + imagePath));
            imageView.setImage(image);
            observers.forEach(observer -> observer.updateImageChange(this.elementType, image));
        } catch (Exception e) {
            // Error Handling Complete
            new Alert(Alert.AlertType.ERROR, myResources.getString(ERROR_IMAGE)).showAndWait();
        }
    }

    private void initMainNode(Node... nodes) {
        mainNode.setAlignment(Pos.CENTER);
        mainNode.setPadding(new Insets(PADDING_AMOUNT));
        mainNode.getChildren().addAll(nodes);
    }

    private void initImageView(String imagePath) {
        imageView = new ImageView();
        imageView.setFitWidth(IMAGE_SIZE);
        imageView.setFitHeight(IMAGE_SIZE);
        imagePath = imagePath.replace("\\", "/");
        this.imagePath = imagePath;
        Image image;
        try {
            image = new Image(new FileInputStream(System.getProperty(USER_DIR) + imagePath));
            imageView.setImage(image);
        } catch (Exception e) {
            // Error Handling Complete
            new Alert(Alert.AlertType.ERROR, myResources.getString(ERROR_IMAGE)).showAndWait();
        }
    }
}
