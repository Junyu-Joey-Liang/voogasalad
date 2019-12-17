package voogasalad.authoring.controller.gamebuilder;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import voogasalad.authoring.authoringfeature.GameMacroFeature;
import voogasalad.authoring.util.StorageUtil;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.VisualizationUtil;
import voogasalad.authoring.util.elementbuilders.InputTextElement;
import voogasalad.authoring.util.elementbuilders.AuthoringTextArea;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;

import static voogasalad.authoring.util.VisConstant.*;


public class GameControllerCentralPane implements GameControllerPane {
    private static final String defaultImagePath = "/voogasalad/authoring/resources/gameimages/defaultGame.png";
    private final String defaultImgUrl = this.getClass().getResource(defaultImagePath).toString();
    private VBox centralPane;
    private InputTextElement gameNameInput;
    private Text gameNameText;
    private Text gameDescriptionText;
    private AuthoringTextArea gameDesInput;
    private ImageView currentGameIcon;
    private GameController controller;

    public GameControllerCentralPane(GameController controller) {
        this.controller = controller;
        centralPane = VisualizationUtil.createVBox(Arrays.asList(VisConstant.gameObjectBundle.getString("gameControllerCentralPane").split(",")).iterator());
        currentGameIcon = new ImageView(new Image(defaultImgUrl));
        currentGameIcon.setFitHeight(HUGE_ICON);
        currentGameIcon.setFitWidth(HUGE_ICON);

        Button chooseImageButton = VisualizationUtil.makeButton("Select Image", e -> selectImage(e));

        gameNameInput = new InputTextElement("Game Name");
        gameNameInput.getTextField().textProperty().addListener((obs, old, newVal) -> changeName(newVal));

        gameDesInput = new AuthoringTextArea("Game\nDescription");
        gameDesInput.getTextArea().textProperty().addListener((obs, old, newVal) -> changeDescription(gameDesInput.getTextArea().getText()));


        gameNameText = new Text();
        gameNameText.setFont(new Font(TITLE_SIZE));

        gameDescriptionText = new Text();

        centralPane.getChildren().addAll(currentGameIcon, chooseImageButton, gameNameText, gameDescriptionText, gameNameInput.getNode(), gameDesInput.getHBox());

    }

    private void selectImage(ActionEvent e) {
        File file = StorageUtil.getFilePrompt(e);
        if (file != null) {
            String imagePath = VisualizationUtil.copyImageAndReturnNewFilePath(file, GAME_IMAGES_FOLDER);
            controller.getGameMacroFeature().setImagePath(imagePath);
            updateGameIcon(VisualizationUtil.initImageView(imagePath));
        }
    }

    private void changeName(String name) {
        controller.getGameMacroFeature().setName(name);
        gameNameText.setText(name);
    }

    private void changeDescription(String des) {
        controller.getGameMacroFeature().setDescription(des);
        gameDescriptionText.setText(des);
    }

    private void updateGameIcon(ImageView img) {
        currentGameIcon.setImage(img.getImage());
        currentGameIcon.setFitHeight(HUGE_ICON);
        currentGameIcon.setFitWidth(HUGE_ICON);
    }

    @Override
    public Pane getPane() {
        return centralPane;
    }

    @Override
    public void updateOnLoadGame() {
        GameMacroFeature gameMacroFeature = controller.getGameMacroFeature();
        try {
            currentGameIcon = new ImageView(new Image(new File(gameMacroFeature.getImagePath()).toURI().toURL().toString()));
        } catch (MalformedURLException e) {
            // TODO
        }
        gameNameInput.getTextField().setText(gameMacroFeature.getName());
        gameNameText.setText(gameMacroFeature.getName());
        gameDesInput.getTextArea().setText(gameMacroFeature.getDescription());
        gameDescriptionText.setText(gameMacroFeature.getDescription());
    }
}
