package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.levelrules;

import javafx.scene.Node;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import voogasalad.authoring.authoringfeature.enumtypes.EndConditionType;
import voogasalad.authoring.authoringfeature.enumtypes.ScoringRuleType;
import voogasalad.authoring.controller.Viewable;
import voogasalad.authoring.util.elementbuilders.InputIntegerSliderElement;
import voogasalad.authoring.util.elementbuilders.ToggleGroupInputElement;

import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class LevelRulesElementView implements Viewable {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String SCORING_RULE_LABEL = "scoringRuleLabel";
    public static final String END_CONDITION_LABEL = "endConditionLabel";
    public static final String STARTING_LIVES_LABEL = "startingLivesLabel";
    public static final String STARTING_MONEY_LABEL = "startingMoneyLabel";
    public static final String GAME_TIME_LABEL = "attackerFlowTimeLabel";
    public static final double THRESHOLD = 0.001;
    public static final int MIN_VAL = 1;
    public static final int MAX_LIVES = 100;
    public static final int MAX_MONEY = 10000;
    public static final int MAX_TIME = 600;
    public static final int DEFAULT_TIME = 100;
    public static final int PREF_SLIDER_WIDTH = 200;

    private ToggleGroupInputElement scoringRuleToggle;
    private ToggleGroupInputElement endConditionToggle;
    private InputIntegerSliderElement livesSlider;
    private InputIntegerSliderElement moneySlider;
    private InputIntegerSliderElement gameTimeSlider;
    private VBox mainNode;

    private Set<LevelRulesElementViewObserver> observers;

    public LevelRulesElementView(LevelRulesElementViewObserver observer) {
        ResourceBundle displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        observers = new HashSet<>();
        observers.add(observer);
        scoringRuleToggle = new ToggleGroupInputElement(displayInfo.getString(SCORING_RULE_LABEL), null);
        Arrays.stream(ScoringRuleType.values()).forEach(value -> scoringRuleToggle.addToggle(value.toString()));
        endConditionToggle = new ToggleGroupInputElement(displayInfo.getString(END_CONDITION_LABEL), ((observable, oldValue, newValue) -> endConditionChange(((RadioButton) newValue).getText())));
        Arrays.stream(EndConditionType.values()).forEach(value -> endConditionToggle.addToggle(value.toString()));
        livesSlider = new InputIntegerSliderElement(displayInfo.getString(STARTING_LIVES_LABEL), MIN_VAL, MAX_LIVES, MIN_VAL, null, PREF_SLIDER_WIDTH);
        moneySlider = new InputIntegerSliderElement(displayInfo.getString(STARTING_MONEY_LABEL), MIN_VAL, MAX_MONEY, MIN_VAL, null, PREF_SLIDER_WIDTH);
        gameTimeSlider = new InputIntegerSliderElement(displayInfo.getString(GAME_TIME_LABEL), MIN_VAL, MAX_TIME, DEFAULT_TIME, ((observable, oldValue, newValue) -> gameTimeChange((double) newValue)), PREF_SLIDER_WIDTH);
        mainNode = new VBox();
        mainNode.getChildren().addAll(scoringRuleToggle.getNode(), endConditionToggle.getNode(), livesSlider.getNode(), moneySlider.getNode(), gameTimeSlider.getNode());
    }

    @Override
    public Node getNode() {
        return mainNode;
    }

    public String getScoringRule() {
        return scoringRuleToggle.getVal();
    }

    public void setScoringRule(String scoringRule) {
        scoringRuleToggle.setVal(scoringRule);
    }

    public String getEndCondition() {
        return endConditionToggle.getVal();
    }

    public void setEndCondition(String endCondition) {
        endConditionToggle.setVal(endCondition);
    }

    public int getStartingMoney() {
        return moneySlider.getVal();
    }

    public void setStartingMoney(int startingMoney) {
        moneySlider.setVal(startingMoney);
    }

    public int getStartingLives() {
        return livesSlider.getVal();
    }

    public void setStartingLives(int lives) {
        livesSlider.setVal(lives);
    }

    public int getGameTime() {
        return gameTimeSlider.getVal();
    }

    public void setGameTime(int gameTime) {
        gameTimeSlider.setVal(gameTime);
    }

    private void gameTimeChange(double levelTime) {
        if (Math.abs(levelTime - (int) levelTime) < THRESHOLD) {
            observers.forEach(observer -> observer.updateLevelTimeChange((int) levelTime));
        }
    }

    private void endConditionChange(String endCondition) {
        observers.forEach(observer -> observer.updateEndConditionChange(EndConditionType.valueOf(endCondition)));
    }
}
