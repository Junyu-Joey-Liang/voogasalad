package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.levelrules;

import voogasalad.authoring.authoringfeature.enumtypes.EndConditionType;

public interface LevelRulesElementObserver {
    void updateEndCondition(EndConditionType endCondition);

    void updateLevelTime(int levelTime);
}
