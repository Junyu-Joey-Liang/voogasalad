package voogasalad.authoring.util.elementbuilders;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import voogasalad.authoring.util.elementbuilders.resources.ElementBuilderConstants;

import java.io.FileInputStream;
import java.util.ResourceBundle;

public class LabelledImageElement {
    private static final String CSS_TAG = "labelled-image-element";
    private static final String ERROR_IMAGE_FOLDER = "/src/voogasalad/authoring/resources/defaultimage/";
    private static final String ERROR_IMAGE_PATH = "ERROR_IMAGE.png";
    private static final String USER_DIR = "user.dir";
    private static final String ERROR_IMAGE_KEY = "defaultImageError";

    private VBox mainNode;
    private Label label;
    private ImageView imageView;
    private ResourceBundle errorMessages;

    public LabelledImageElement(String imageName, String imagePath, int imageSize) {
        errorMessages = ResourceBundle.getBundle(ElementBuilderConstants.ERROR_MESSAGE_FILE);
        mainNode = new VBox();
        mainNode.getStylesheets().add(getClass().getResource(ElementBuilderConstants.ELEMENT_BUILDER_CSS_FILE).toExternalForm());
        mainNode.getStyleClass().add(CSS_TAG);

        label = new Label(imageName);

        imageView = new ImageView(getImage(imagePath));
        imageView.setFitWidth(imageSize);
        imageView.setFitHeight(imageSize);

        mainNode.getChildren().addAll(imageView, label);
    }

    public Node getNode() {
        return mainNode;
    }

    public void setName(String name) {
        label.setText(name);
    }

    public void setImage(String imagePath) {
        imageView.setImage(getImage(imagePath));
    }

    private Image getImage(String imagePath) {
        Image image = null;
        try {
            imagePath = imagePath.replace("\\", "/");
            image = new Image(new FileInputStream(System.getProperty(USER_DIR) + imagePath));
        } catch (Exception e) {
            try {
                image = new Image(new FileInputStream(System.getProperty(USER_DIR) + ERROR_IMAGE_FOLDER + ERROR_IMAGE_PATH));
            } catch (Exception ee) {
                new Alert(Alert.AlertType.ERROR, errorMessages.getString(ERROR_IMAGE_KEY)).showAndWait();
                //SHOULD NOT BE POSSIBLE TO GET HERE.
            }
        }
        return image;
    }
}
