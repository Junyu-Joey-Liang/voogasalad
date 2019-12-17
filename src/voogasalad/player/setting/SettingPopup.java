package voogasalad.player.setting;

import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import voogasalad.player.IMediator;
import voogasalad.player.PlayerCommandFactory;
import voogasalad.player.util.ImageMaker;
import java.util.ResourceBundle;

public class SettingPopup {

    private static final String RESOURCE_PATH = "voogasalad.player.resources.SettingPopupResource";
    private static final String HAND = "-fx-cursor: hand";
    private static final double HEIGHT = 440;
    private static final double WIDTH = 200;

    private Stage stage = new Stage();
    private ResourceBundle myResources;
    private VBox vBox = new VBox();
    private PlayerCommandFactory myPlayerCommandFactory;

    public SettingPopup(IMediator mediator) {
        myResources = ResourceBundle.getBundle(RESOURCE_PATH);
        myPlayerCommandFactory = new PlayerCommandFactory(mediator);
        initialize();
    }

    public void show() {
       stage.show();
    }

    private void initialize() {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(new Scene(vBox));
        vBox.setPrefWidth(WIDTH);
        vBox.setPrefHeight(HEIGHT);
        createButtons();
    }

    private void createButtons() {
        for(String key : myResources.keySet()) {
            String[] arr = myResources.getString(key).split(",");
            ImageView buttonImage = createImageView(arr[0], Double.parseDouble(arr[1]), (Double.parseDouble(arr[2])),
                    Double.parseDouble(arr[3]), Double.parseDouble(arr[4]));
            vBox.getChildren().add(buttonImage);
            buttonImage.setOnMouseClicked(e -> callAction(key));
            buttonImage.setStyle(HAND);
        }
    }

    private void callAction(String property) {
        stage.close();
        myPlayerCommandFactory.execute(property);
    }

    private ImageView createImageView(String filePath, double XLoc, double YLoc, double width, double height) {
        ImageView imageView = new ImageView(ImageMaker.getImage(filePath));
        imageView.setLayoutX(XLoc);
        imageView.setLayoutY(YLoc);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }

    public Stage getStage() {
        return stage;
    }
}
