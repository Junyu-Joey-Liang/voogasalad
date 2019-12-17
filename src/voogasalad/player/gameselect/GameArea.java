package voogasalad.player.gameselect;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class GameArea extends VBox {

    private static final double WIDTH = 270;
    private static final double HEIGHT = 270;
    private static final double IMAGE_HEIGHT = 200;
    private static final double LABEL_HEIGHT = 20;
    private static final double PADDING = 5;
    private static final double SPACING = 5;

    public GameArea(GameInformation gameInformation) {
        super();
        initialize(gameInformation);
    }

    private void initialize(GameInformation gameInformation) {
        setOnMouseClicked(e -> System.out.println(gameInformation.getTitle()));
        setPrefHeight(HEIGHT);
        setPrefWidth(WIDTH);
        setPadding(new Insets(PADDING));
        setSpacing(SPACING);
        getChildren().addAll(getMyGameImage(gameInformation.getImage()), getLabel(gameInformation.getTitle()),
                getLabel(gameInformation.getDescription()));
    }

    private ImageView getMyGameImage(Image image) {
        ImageView gameImage = new ImageView(image);
        gameImage.setPreserveRatio(true);
        gameImage.setFitHeight(IMAGE_HEIGHT);
        return gameImage;
    }

    private Label getLabel(String text) {
        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setPrefHeight(LABEL_HEIGHT);
        return label;
    }
}
