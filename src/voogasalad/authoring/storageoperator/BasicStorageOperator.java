package voogasalad.authoring.storageoperator;


import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.GameMacroFeature;
import voogasalad.authoring.model.Model;
import voogasalad.data.Data;
import voogasalad.data.GameData;

import java.util.Map;

/**
 * implementation of storage operator
 */
public class BasicStorageOperator implements StorageOperator {
    private Model model;
    private Data data;


    /**
     * default constructor
     *
     * @param model data model for this game
     */
    public BasicStorageOperator(Model model) {
        this.model = model;
    }


    @Override
    public void save(String filePath) {
        this.data = new GameData(filePath);
        GameMacroFeature gameMacroFeature = (GameMacroFeature) model.getAuthoringFeatures("GameMacro").iterator().next();
        data.saveData(gameMacroFeature);
    }

    @Override
    public void load(String filePath) {
        this.data = new GameData(filePath);
        GameMacroFeature gameMacroFeature = data.retrieveGameMacro();
        Map<Integer,AuthoringFeature> map = gameMacroFeature.getIdMap();
        this.model.load(map);
    }
}
