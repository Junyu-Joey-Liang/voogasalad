package voogasalad.data.converter.game;

import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.LevelFeature;
import voogasalad.authoring.authoringfeature.enumtypes.EndConditionType;
import voogasalad.authoring.authoringfeature.enumtypes.ScoringRuleType;
import voogasalad.data.converter.element.ElementConverterFactory;
import voogasalad.gameengine.game.Game;
import voogasalad.gameengine.game.Level;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class is used to build levels for the Game object to use.
 *
 * @author Justin Havas
 */
public class Levels {
    private final String BUNDLE_PATH = "voogasalad.data.properties.ConverterNames";
    private ResourceBundle myBundle = ResourceBundle.getBundle(BUNDLE_PATH);

    private Map<Integer,AuthoringFeature> idMap;
    private MapInstanceConverter mapInstanceConverter;

    public Levels(Map<Integer,AuthoringFeature> idMap) {
        this.idMap = idMap;
        mapInstanceConverter = new MapInstanceConverter(this.idMap);
    }

    /**
     * Converts the various level features specified by the provided list into levels that are added to the Game.
     *
     * @param myLevels list of integers indexed by level number and the integers in the list are the IDs of the level features
     * @param game Game object to be initialized
     */
    public void convert(List<Integer> myLevels, Game game) {
        game.clearLevels();
        for (int i = 0; i < myLevels.size(); i++) {
            Level level = new Level();
            LevelFeature levelFeature = (LevelFeature) getAuthoringFeatureWithId(myLevels.get(i));
            // convert level dependent elements like attackers, defenders, obstacles
            for (String s : Collections.list(myBundle.getKeys())) {
                level.addAvailableElements(new ElementConverterFactory().makeConverter(myBundle.getString(s), this).convert(levelFeature));
            }
            initializeLevelAttributes(level, levelFeature);
            new Maps().convert(getAuthoringFeatureWithId(levelFeature.getIntValue(LevelFeature.MAP_ID)), level);
            game.addLevel(level);
        }
        game.resetCurrentLevel();
    }

    /**
     * Returns an authoring feature based off the provided ID.
     *
     * @param i the unique ID of an authoring feature in the idMap
     */
    public AuthoringFeature getAuthoringFeatureWithId(Integer i) {
        return idMap.get(i);
    }

    private void initializeLevelAttributes(Level level, LevelFeature levelFeature) {
        level.setAttackerOrder(mapInstanceConverter.convertAttackerOrderMapFromIdsToNames(levelFeature.getAttackerSpecificFlow()));
        level.setStartingHealth(levelFeature.getIntValue(LevelFeature.STARTING_LIVES));
        level.setStartingMoney(levelFeature.getIntValue(LevelFeature.STARTING_MONEY));
        level.setIncome(levelFeature.getIntValue(LevelFeature.TIME_INCOME));
        level.setInfinite(levelFeature.getEndCondition().toString().equals(EndConditionType.INFINITE.toString()));
        level.setTimeScore(levelFeature.getScoringRule().toString().equals(ScoringRuleType.TIME.toString()));
        level.setMultiplier(mapInstanceConverter.convertMapFromIdsToNames(levelFeature.getFeatureValue(LevelFeature.ATTACKER_FLOW_MULTIPLIER)));
        level.setScores(mapInstanceConverter.convertMapFromIdsToNames(levelFeature.getFeatureValue(LevelFeature.ATTACKER_VALUES)));
        level.setCost(mapInstanceConverter.convertCostMapsFromIdsToNames(levelFeature));
        level.setAttackerIncome(mapInstanceConverter.convertMapFromIdsToNames(levelFeature.getFeatureValue(LevelFeature.ATTACKER_INCOMES)));
    }
}
