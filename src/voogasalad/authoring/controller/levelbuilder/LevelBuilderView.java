package voogasalad.authoring.controller.levelbuilder;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import voogasalad.authoring.util.VisualizationUtil;

import java.util.ResourceBundle;

public class LevelBuilderView {
    public static final String CSS_TAG = "main-border-pane";
    public static final String LEVEL_BUILDER_CSS_FILE = "/voogasalad/authoring/controller/levelbuilder/resources/LevelBuilderStylesheet.css";
    public static final String RESOURCES_FOLDER = "voogasalad.authoring.controller.levelbuilder.resources.";
    public static final String DISPLAYED_RESOURCES = "DisplayInfo";
    public static final String STORE_BUTTON_NAME = "StoreButton";
    public static final String STORE_BUTTON_ID = "LevelStoreButton";
    public static final String CSS_TAG_ELEMENT = "borderedBox";
    public static final String CSS_TAG_LABEL = "boldLabel";
    public static final String CSS_TAG_CONTENT_NODE = "contentNode";

    private VBox elementsPane;
    private BorderPane mainPane;
    private ComboBox<String> levelSelector;

    private LevelBuilderViewObserver controller;

    public LevelBuilderView(LevelBuilderViewObserver myController) {
        ResourceBundle displayedInfo = ResourceBundle.getBundle(RESOURCES_FOLDER + DISPLAYED_RESOURCES);
        controller = myController;
        mainPane = new BorderPane();
        mainPane.getStylesheets().add(getClass().getResource(LEVEL_BUILDER_CSS_FILE).toExternalForm());
        mainPane.getStyleClass().add(CSS_TAG);
        elementsPane = new VBox();
        ScrollPane elementsScrollPane = new ScrollPane(elementsPane);
        HBox toolPane = new HBox();
        levelSelector = new ComboBox<>();
        levelSelector.valueProperty().addListener((obs, old, neww) -> this.controller.loadLevel(neww));
        Button storeButton = VisualizationUtil.makeButton(displayedInfo.getString(STORE_BUTTON_NAME), e -> this.controller.storeLevel());
        storeButton.setId(STORE_BUTTON_ID);

        toolPane.getChildren().addAll(levelSelector, storeButton);
        mainPane.setTop(toolPane);
        mainPane.setCenter(elementsScrollPane);
    }

    public void addElement(Node node, String nodeName) {
        VBox nodeBox = new VBox();
        nodeBox.getStyleClass().add(CSS_TAG_ELEMENT);
        Label nameLabel = new Label(nodeName);
        nameLabel.getStyleClass().add(CSS_TAG_LABEL);
        node.getStyleClass().add(CSS_TAG_CONTENT_NODE);
        nodeBox.getChildren().addAll(nameLabel, node);
        elementsPane.getChildren().add(nodeBox);
    }

    public void addLevel(String levelName) {
        if (!levelSelector.getItems().contains(levelName)) {
            levelSelector.getItems().add(levelName);
        }
        levelSelector.getSelectionModel().select(levelName);
    }

    public Node getNode() {
        return mainPane;
    }
}
