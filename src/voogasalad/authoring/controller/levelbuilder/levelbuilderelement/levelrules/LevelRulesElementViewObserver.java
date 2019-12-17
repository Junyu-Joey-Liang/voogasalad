package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.levelrules;

import voogasalad.authoring.authoringfeature.enumtypes.EndConditionType;

public interface LevelRulesElementViewObserver {
    void updateEndConditionChange(EndConditionType endCondition);

    void updateLevelTimeChange(int levelTime);
}
