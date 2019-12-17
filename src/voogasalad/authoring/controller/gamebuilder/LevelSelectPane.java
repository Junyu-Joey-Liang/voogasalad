package voogasalad.authoring.controller.gamebuilder;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import voogasalad.authoring.util.ReversableMap;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.VisualizationUtil;
import voogasalad.authoring.util.elementbuilders.AuthoringChoice;
import voogasalad.authoring.util.elementbuilders.AuthoringVerticalScroll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class LevelSelectPane implements GameControllerPane {
    ResourceBundle rb = VisConstant.gameObjectBundle;
    private VBox vBox;
    private Text title;
    private AuthoringVerticalScroll verticalScroll;
    private Button addLevel;
    private ReversableMap<Integer, String> idToLevelMap;
    private GameController controller;

    public LevelSelectPane(GameController controller) {
        idToLevelMap = new ReversableMap<>();
        // TODO: initialize level map

        this.controller = controller;
        title = new Text("Select Levels");
        vBox = VisualizationUtil.createVBox(Arrays.asList(rb.getString("LevelSelectPane").split(",")).iterator());
        verticalScroll = new AuthoringVerticalScroll();
        addLevel = VisualizationUtil.makeButton("add level", e -> addNewLevel());

        vBox.getChildren().addAll(title, verticalScroll.getScrollPane(), addLevel);
    }

    private void addNewLevel() {
        verticalScroll.getCol().getChildren().add(createLevelChoice().getHBox());
        updateLevelLabel();
        setLevelChoice();
    }

    private AuthoringChoice createLevelChoice() {
        ArrayList<String> levelNames = new ArrayList<>();
        for (int id : idToLevelMap.keySet()) {
            levelNames.add(idToLevelMap.get(id));
        }
        AuthoringChoice r = new AuthoringChoice("", levelNames.toArray(new String[idToLevelMap.keySet().size()]));
        r.getChoiceBox().valueProperty().addListener(e -> setLevelChoice());
        Button cancelButton = VisualizationUtil.makeCancelButton("remove", e -> deleteLevel(r));
        r.getHBox().getChildren().add(cancelButton);
        return r;
    }

    public void setLevelChoice() {
        List<Integer> levellist = new ArrayList<>();
        for (Node n : verticalScroll.getCol().getChildren()) {
            HBox hBox = (HBox) n;
            ChoiceBox choiceBox = (ChoiceBox) hBox.getChildren().get(1);
            if (choiceBox.getValue() != null) {
                String levelName = choiceBox.getValue().toString();
                levellist.add(idToLevelMap.getKey(levelName));
            }
        }
        controller.getGameMacroFeature().setLevelList(levellist);
    }

    private void deleteLevel(AuthoringChoice r) {
        verticalScroll.getCol().getChildren().remove(r.getHBox());
        updateLevelLabel();
        setLevelChoice();
    }

    private void updateLevelLabel() {
        int level = 0;
        for (Node n : verticalScroll.getCol().getChildren()) {
            HBox hBox = (HBox) n;
            Label label = (Label) hBox.getChildren().get(0);
            label.setText("Level " + level);
            level++;
        }
    }

    @Override
    public Pane getPane() {
        return vBox;
    }

    @Override
    public void updateOnLoadGame() {
        idToLevelMap = controller.getLevelMap();
        verticalScroll.getCol().getChildren().clear();
        List<Integer> list = controller.getGameMacroFeature().getLevelList();
        if (list != null) {
            for (int levelid : list) {
                AuthoringChoice authoringChoice = createLevelChoice();
                authoringChoice.getChoiceBox().setValue(idToLevelMap.get(levelid));
                verticalScroll.getCol().getChildren().add(authoringChoice.getHBox());
            }
            updateLevelLabel();
        }
        setLevelChoice();
    }

}
