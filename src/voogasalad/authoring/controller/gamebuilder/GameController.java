package voogasalad.authoring.controller.gamebuilder;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.GameMacroFeature;
import voogasalad.authoring.controller.Controller;
import voogasalad.authoring.controller.LevelObserver;
import voogasalad.authoring.model.Model;
import voogasalad.authoring.storageoperator.BasicStorageOperator;
import voogasalad.authoring.storageoperator.StorageOperator;
import voogasalad.authoring.util.ReversableMap;
import voogasalad.authoring.util.StorageUtil;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.VisualizationUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class GameController implements Controller, LevelObserver {
    private Set<Object> observers = new HashSet<>();
    private BorderPane pane;
    private GameControllerCentralPane centralPane;
    private Model model;
    private StorageOperator storageOperator;
    private GameMacroFeature gameMacroFeature;
    private LevelSelectPane levelPane;
    private String type = "GameMacro";
    private String name = VisConstant.gameObjectBundle.getString("gameControllerName");

    public GameController(Model model) {
        this.model = model;
        this.storageOperator = new BasicStorageOperator(model);
        gameMacroFeature = new GameMacroFeature();
        initPane();

    }

    private void initPane() {
        this.pane = new BorderPane();
        centralPane = new GameControllerCentralPane(this);
        pane.setCenter(centralPane.getPane());
        initRight();
        initLeft();
    }

    private void initLeft() {
        levelPane = new LevelSelectPane(this);
        pane.setLeft(levelPane.getPane());
    }

    private void initRight() {

        VBox rightPane = VisualizationUtil.createVBox(Arrays.asList(VisConstant.gameObjectBundle.getString("GamerightCol").split(",")).iterator());
        this.pane.setRight(rightPane);

        Button saveButton = VisualizationUtil.makeButton("Save Game", e -> saveGame(e));
        Button loadButton = VisualizationUtil.makeButton("Load Game", e -> loadGame(e));

        rightPane.getChildren().addAll(saveButton, loadButton);
    }


    private void saveGame(ActionEvent e) {
        if (gameMacroFeature.getDescription() == null || gameMacroFeature.getName() == null || gameMacroFeature.getLevelList()==null || gameMacroFeature.getLevelList().size()==0) {
            VisualizationUtil.showAlert("The game needs a level, a name and a description");
            return;
        }
        gameMacroFeature.setIdMap(model.getIDMap());
//        model.addAuthoringFeature("GameMacro", gameMacroFeature);

        if (gameMacroFeature.getID() != -1) { //current object have been added to model before, inherent old id
            model.replaceAuthoringFeature(type, gameMacroFeature.getID(), gameMacroFeature);
        } else { //current object not in model, get new id
            model.addAuthoringFeature(type, gameMacroFeature);
        }

        levelPane.setLevelChoice();

        File file = StorageUtil.getDirectoryPrompt(e);
        if (file != null) {
            storageOperator.save(file.getPath() + "/" + gameMacroFeature.getName() + ".xml");
        }
    }

    private void loadGame(ActionEvent e) {
        File file = StorageUtil.getFilePrompt(e);
        if (file != null) {
            storageOperator.load(file.getPath());
            updateObservers();
            gameMacroFeature = (GameMacroFeature) model.getAuthoringFeatures(type).iterator().next();
            centralPane.updateOnLoadGame();
            levelPane.updateOnLoadGame();
        }
    }

    private void updateObservers() {
        for (Object obj : observers) {
            Controller controller = (Controller) obj;
            controller.load();
        }
    }


    /**
     * @return map from level name to id
     */
    public ReversableMap<Integer, String> getLevelMap() {
        ReversableMap<Integer, String> map = new ReversableMap<>();
        Set<AuthoringFeature> set = model.getAuthoringFeatures("LevelFeature");
        for (AuthoringFeature level : set) {
            map.put(level.getID(), level.getName());
        }
        return map;
    }

    @Override
    public void addObserver(Object observer) {
        observers.add(observer);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Node getNode() {
        return pane;
    }

    @Override
    public void load() {
        // nothing needs to be down in the game controller.
    }

    public GameMacroFeature getGameMacroFeature() {
        return gameMacroFeature;
    }

    @Override
    public void updateLevels() {
        levelPane.updateOnLoadGame();
    }
}
