package voogasalad.player;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import voogasalad.player.util.ImageMaker;

public class DefenderBlock {
    private static final double WIDTH = 200;
    private static final double HEIGHT = 110;
    private static final double PADDING = 5;
    private static final double SPACING = 5;
    private static final double IMAGE_HEIGHT = 70;
    private static final String DEFENDER_BOX = "defender-box";

    private ImageView myImageView;
    private String defenderName;
    private int cost;
    private VBox myVBox = new VBox();

    public DefenderBlock(String filePath, String defenderName, int cost) {
        this.cost = cost;
        this.myImageView = new ImageView(ImageMaker.getImage(filePath));
        this.defenderName = defenderName;
        initialize();
    }

    public Node getNode() {
        return myVBox;
    }

    public int getCost() {return cost;}

    private void initialize() {
        myVBox.setPadding(new Insets(PADDING));
        myVBox.setPrefHeight(HEIGHT);
        myVBox.setPrefWidth(WIDTH);
        myVBox.setSpacing(SPACING);
        myVBox.setAlignment(Pos.CENTER);
        myImageView.setFitHeight(IMAGE_HEIGHT);
        myImageView.setPreserveRatio(true);
        myVBox.getStyleClass().add(DEFENDER_BOX);
        myVBox.getChildren().addAll(myImageView, createLabel());
    }

    private Label createLabel() {
        Label label = new Label(defenderName + " (" + cost + ")");
        label.setTextAlignment(TextAlignment.CENTER);
        return label;
    }
}
