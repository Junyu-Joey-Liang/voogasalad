package voogasalad.player.gameselect;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import voogasalad.player.IMediator;

import java.util.ResourceBundle;

public class GameSelectScreen {
    private static final String RESOURCE_PATH = "voogasalad.player.resources.GameSelectResource";
    private static final String STYLE_SHEET = "/voogasalad/player/resources/stylesheet/GameSelectStyle.css";
    private static final String TITLE = "Title";
    private static final double SCREEN_WIDTH = 900;
    private static final double SCREEN_HEIGHT = 700;
    private static final double TITLE_BOX_HEIGHT = 50;

    private VBox rootNode;
    private ResourceBundle myResourceBundle;
    private GameLibraryDisplay myGameLibraryDisplay;

    public GameSelectScreen(IMediator controller) {
        myResourceBundle = ResourceBundle.getBundle(RESOURCE_PATH);
        myGameLibraryDisplay = new GameLibraryDisplay(controller);
        rootNode = new VBox();
        createStage();
    }

    private void createStage() {
        rootNode = new VBox();
        rootNode.setPrefSize(SCREEN_WIDTH,SCREEN_HEIGHT);
        rootNode.getStylesheets().add(getClass().getResource(STYLE_SHEET).toExternalForm());
        rootNode.getStyleClass().add("root");
        createTitle();
        createGameLibraryPane();
        createExitButton();
    }

    private void createExitButton() {
        Button exitButton = new Button("Refresh");
        exitButton.setOnAction(event ->  {
            myGameLibraryDisplay.refresh();
        });
        rootNode.getChildren().add(exitButton);
    }

    private void createTitle() {
        HBox hBox = new HBox();
        hBox.setPrefHeight(TITLE_BOX_HEIGHT);
        hBox.getChildren().add(titleLabel());
        hBox.setAlignment(Pos.CENTER);
        rootNode.getChildren().add(hBox);
    }

    private void createGameLibraryPane() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(myGameLibraryDisplay);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        rootNode.getChildren().add(scrollPane);
    }

    private Label titleLabel() {
        String[] titleStrings = myResourceBundle.getString(TITLE).split(",");
        Label label = new Label(titleStrings[0]);
        label.getStyleClass().add(titleStrings[1]);
        return label;
    }

    public Node getNode() {
        return rootNode;
    }
}