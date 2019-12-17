package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.levelrules;

import javafx.scene.Node;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.enumtypes.EndConditionType;
import voogasalad.authoring.controller.levelbuilder.LevelBuildingConstants;
import voogasalad.authoring.controller.levelbuilder.LevelConfigurationException;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.LevelBuilderElement;
import voogasalad.authoring.util.elementbuilders.ToggleSelectionException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class LevelRulesElement implements LevelBuilderElement, LevelRulesElementViewObserver {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String ERROR_SCORING_RULE = "scoringRuleError";
    public static final String ERROR_END_CONDITION = "endConditionError";
    public static final String LEVEL_RULES_NAME = "levelRulesName";

    private ResourceBundle displayInfo;
    private LevelRulesElementView myView;
    private Set<LevelRulesElementObserver> observers;

    public LevelRulesElement() {
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        myView = new LevelRulesElementView(this);
        observers = new HashSet<>();
    }

    @Override
    public Object getVal() {
        Map<String, Object> outputMap = new HashMap<>();
        try {
            outputMap.put(LevelBuildingConstants.SCORING_RULE, myView.getScoringRule());
        } catch (ToggleSelectionException e) {
            throw new LevelConfigurationException(e, displayInfo.getString(ERROR_SCORING_RULE));
        }
        try {
            outputMap.put(LevelBuildingConstants.END_CONDITION, myView.getEndCondition());
        } catch (ToggleSelectionException e) {
            throw new LevelConfigurationException(e, displayInfo.getString(ERROR_END_CONDITION));
        }
        outputMap.put(LevelBuildingConstants.STARTING_MONEY, myView.getStartingMoney());
        outputMap.put(LevelBuildingConstants.STARTING_LIVES, myView.getStartingLives());
        outputMap.put(LevelBuildingConstants.ATTACKER_FLOW_TIME, myView.getGameTime());
        return outputMap;
    }

    @Override
    public void setVal(Object object) {
        Map<String, Object> inputMap = (Map<String, Object>) object;
        myView.setScoringRule((String) inputMap.get(LevelBuildingConstants.SCORING_RULE));
        myView.setEndCondition((String) inputMap.get(LevelBuildingConstants.END_CONDITION));
        myView.setStartingMoney((int) inputMap.get(LevelBuildingConstants.STARTING_MONEY));
        myView.setStartingLives((int) inputMap.get(LevelBuildingConstants.STARTING_LIVES));
        myView.setGameTime((int) inputMap.get(LevelBuildingConstants.ATTACKER_FLOW_TIME));
    }

    @Override
    public void addObservers(Object... objects) {
        Arrays.asList(objects).forEach(observer -> observers.add((LevelRulesElementObserver) observer));
    }

    @Override
    public void updateElement(Set<AuthoringFeature> authoringFeatures) {
        //Do nothing
    }

    @Override
    public Node getNode() {
        return myView.getNode();
    }

    @Override
    public String getName() {
        return displayInfo.getString(LEVEL_RULES_NAME);
    }

    @Override
    public void updateEndConditionChange(EndConditionType endCondition) {
        observers.forEach(observer -> observer.updateEndCondition(endCondition));
    }

    @Override
    public void updateLevelTimeChange(int levelTime) {
        observers.forEach(observer -> observer.updateLevelTime(levelTime));
    }
}
