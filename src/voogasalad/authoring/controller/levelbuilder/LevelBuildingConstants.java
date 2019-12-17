package voogasalad.authoring.controller.levelbuilder;

import voogasalad.authoring.authoringfeature.enumtypes.AttackerFlowType;

public class LevelBuildingConstants {
    public static final String SCORING_RULE = "ScoringRule";
    public static final String END_CONDITION = "EndCondition";
    public static final String STARTING_MONEY = "StartingMoney";
    public static final String STARTING_LIVES = "StartingLives";
    public static final String ATTACKER_FLOW_TIME = "GameTime";
    public static final int TIME_INCOME_ID = -1;
    public static final String FLOW_MULTIPLIER = "FlowMultiplier";
    public static final String TIME_SPECIFIC_FLOW = AttackerFlowType.TIME_SPECIFIC.toString();

    private LevelBuildingConstants() {
        //Intentionally Left Blank.
    }

}
