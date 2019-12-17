package voogasalad.player;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import voogasalad.player.util.ImageMaker;

import java.util.ResourceBundle;

public class EndOfGameScreen {

    private static final String BUTTON_ACTION_RESOURCE = "ButtonActions";
    private static final String IMAGE_RESOURCE = "ScreenImage";
    private static final double HEIGHT = 300;
    private static final double WIDTH = 300;
    private static final int FILEPATH_INDEX = 0;
    private static final int HEIGHT_INDEX = 1;
    private static final int WIDTH_INDEX = 2;
    private static final int X_INDEX = 3;
    private static final int Y_INDEX = 4;

    private ResourceBundle buttonResource;
    private ResourceBundle imageResource;
    private Pane myPane;
    private PlayerCommandFactory myPlayerCommandFactory;

    public EndOfGameScreen(String directoryPath, IMediator mediator) {
        buttonResource = ResourceBundle.getBundle(directoryPath + BUTTON_ACTION_RESOURCE);
        imageResource = ResourceBundle.getBundle(directoryPath + IMAGE_RESOURCE);
        myPlayerCommandFactory = new PlayerCommandFactory(mediator);
        initialize();
    }

    public Node getNode() {
        return myPane;
    }

    private void initialize() {
        myPane = new AnchorPane();
        myPane.setPrefSize(WIDTH, HEIGHT);
        createGameOverImage();
        createImageButtons();
        myPane.setStyle("-fx-background-color: linear-gradient(to bottom right, #ffecd2, #fcb69f)");
    }

    private void createGameOverImage() {
        String[] arr = imageResource.getString("image").split(",");
        myPane.getChildren().add(getImageView(arr[FILEPATH_INDEX], Double.parseDouble(arr[HEIGHT_INDEX]), Double.parseDouble(arr[WIDTH_INDEX]),
                Double.parseDouble(arr[X_INDEX]), Double.parseDouble(arr[Y_INDEX])));
    }

    private void createImageButtons() {
        for(String key : buttonResource.keySet()) {
            String[] arr = buttonResource.getString(key).split(",");
            ImageView imageButton = getImageView(arr[FILEPATH_INDEX], Double.parseDouble(arr[HEIGHT_INDEX]), Double.parseDouble(arr[WIDTH_INDEX]),
                    Double.parseDouble(arr[X_INDEX]), Double.parseDouble(arr[Y_INDEX]));
            imageButton.setStyle("-fx-cursor: hand");
            imageButton.setOnMouseClicked(e -> callAction(key));
            myPane.getChildren().add(imageButton);
        }
    }

    private void callAction(String key) {
        terminate();
        myPlayerCommandFactory.execute(key);
    }

    private ImageView getImageView(String filePath, double height, double width, double x, double y) {
        ImageView imageView = new ImageView(ImageMaker.getImage(filePath));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        return imageView;
    }

    private void terminate() {
        ((Pane) myPane.getParent()).getChildren().remove(myPane);
    }
}
