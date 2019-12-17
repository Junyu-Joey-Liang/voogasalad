package voogasalad;

import javafx.application.Preloader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import voogasalad.player.util.ImageMaker;

public class SplashScreen extends Preloader {
    private static final String BACKGROUND_IMAGE = "/src/voogasalad/player/resources/background.png";
    private static final int FONT_SIZE = 23;
    private AnchorPane prePane;
    private Stage preStage;
    private Scene preScene;
    private Label progressLabel;

    public SplashScreen() {
        prePane = new AnchorPane();
        progressLabel = new Label();
        progressLabel.setTextFill(Color.WHITE);
        progressLabel.setFont(Font.font ("Verdana", FONT_SIZE));

        prePane.getChildren().add(progressLabel);
        prePane.setBottomAnchor(progressLabel, 150.0);
        prePane.setLeftAnchor(progressLabel, 300.0);
        setBackground();

    }

    private void setBackground() {
        BackgroundImage backgroundImage= new BackgroundImage(ImageMaker.getImage(BACKGROUND_IMAGE),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        prePane.setBackground(new Background(backgroundImage));
    }


    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        switch (type) {
            case BEFORE_START:
                preStage.hide();
                break;
        }
    }


    @Override
    public void handleApplicationNotification(PreloaderNotification info) {

        if (info instanceof ProgressNotification) {
            progressLabel.setText("Loading " + ((ProgressNotification) info).getProgress() + "%");
        }
    }

    @Override
    public void init() throws Exception {
        preScene = new Scene(prePane, 500, 500);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.preStage = primaryStage;
        preStage.setScene(preScene);
        preStage.initStyle(StageStyle.UNDECORATED);
        preStage.show();

    }
}
