package voogasalad.player;


import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import voogasalad.player.util.ImageMaker;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GameStatusDisplay {
    private static final String RESOURCE_PATH = "voogasalad.player.resources.GameStatusResource";
    private static final double WIDTH = 600;
    private static final double HEIGHT = 20;
    private static final double SPACING = 30;
    private static final double LAYOUT_X = 30;
    private static final double LAYOUT_Y = 20;
    private static final double STATUS_BOX_WIDTH = 100;
    private static final double STATUS_BOX_SPACING = 20;

    private HBox myHBox;
    private Map<String, Label> myStatusLabels = new HashMap<>();
    private ResourceBundle myResourceBundle;

    public GameStatusDisplay() {
        myResourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
        myHBox = new HBox();
        createStatusDisplay();
    }

    public Node getNode() {
        return myHBox;
    }

    public void setStatus(String statusType, String text) {
        myStatusLabels.get(statusType).setText(text);
    }

    public int getStatus(String statusType) {
        return Integer.parseInt(myStatusLabels.get(statusType).getText());
    }

    private void createStatusDisplay() {
        myHBox.setLayoutX(LAYOUT_X);
        myHBox.setLayoutY(LAYOUT_Y);
        myHBox.setPrefHeight(HEIGHT);
        myHBox.setPrefWidth(WIDTH);
        myHBox.setSpacing(SPACING);
        
        for(String key : myResourceBundle.keySet()) {
            myHBox.getChildren().add(createStatusBox(key));
        }
    }

    private HBox createStatusBox(String key) {
        HBox hBox = new HBox();
        hBox.setSpacing(STATUS_BOX_SPACING);
        hBox.setPrefWidth(STATUS_BOX_WIDTH);
        Label label = new Label(key);
        label.setAlignment(Pos.CENTER);
        myStatusLabels.put(key, label);
        ImageView imageView = new ImageView(ImageMaker.getImage(myResourceBundle.getString(key)));
        imageView.setFitWidth(HEIGHT);
        imageView.setPreserveRatio(true);
        hBox.getChildren().addAll(imageView, label);
        return hBox;
    }
}
