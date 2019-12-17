package voogasalad.authoring.authoringfeature;

import javafx.scene.control.Alert;
import voogasalad.authoring.authoringfeature.enumtypes.AttackerFlowType;
import voogasalad.authoring.authoringfeature.enumtypes.EndConditionType;
import voogasalad.authoring.authoringfeature.enumtypes.ScoringRuleType;
import voogasalad.authoring.controller.levelbuilder.LevelBuildingConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class LevelFeature implements AuthoringFeature {
    public static final String ATTACKER = "attacker";
    public static final String DEFENDER = "defender";
    public static final String OBSTACLE = "obstacle";
    public static final String DEFENDER_COSTS = "defendercosts";
    public static final String ATTACKER_VALUES = "attackervalues";
    public static final String OBSTACLE_COSTS = "obstaclecosts";
    public static final String ATTACKER_INCOMES = "attackerincomes";
    public static final String ATTACKER_FLOW_MULTIPLIER = "attackerflowmultiplier";
    public static final String MAP_ID = "mapid";
    public static final String TIME_INCOME = "timeincome";
    public static final String STARTING_LIVES = "startinglives";
    public static final String STARTING_MONEY = "startingmoney";
    private static final String GAME_TIME = "gametime";
    private static final String RESOURCES_FOLDER = "voogasalad.authoring.authoringfeature.resources.";
    private static final String RESOURCE_PATH = "LevelFeatureResources";
    private static final String ERROR_MESSAGES_PATH = "ErrorMessages";
    private static final String METHOD_INVOCATION_ERROR = "MethodInvocationError";
    private static final String DELIMITER = ",";

    private Map<String, Map<Integer, Boolean>> featureAvailabilitiesMap;
    private Map<String, Map<Integer, Integer>> featureValuesMap;
    private Map<String, Integer> intValuesMap;
    private int ID;
    private String name;
    private ResourceBundle myResources;
    private ResourceBundle errorMessages;
    private Map<Integer, Integer> defenderCosts;
    private Map<Integer, Integer> attackerValues;
    private Map<Integer, Integer> obstacleCosts;
    private Map<Integer, Integer> attackerIncomes;
    private Map<Integer, Integer> attackerFlowMultiplier;
    private Integer mapID;
    private Integer timeIncome;
    private Integer startingLives;
    private Integer startingMoney;
    private Integer gameTime;
    private Map<Integer, Boolean> defenderAvailabilities;
    private Map<Integer, Boolean> attackerAvailabilities;
    private Map<Integer, Boolean> obstacleAvailabilities;
    private EndConditionType endCondition;
    private ScoringRuleType scoringRule;
    private Map<Integer, Integer[]> attackerSpecificFlow;

    public LevelFeature() {
        this.myResources = ResourceBundle.getBundle(RESOURCES_FOLDER + RESOURCE_PATH);
        this.errorMessages = ResourceBundle.getBundle(RESOURCES_FOLDER + ERROR_MESSAGES_PATH);
        initPrivateMaps();
    }

    public Map<Integer, Integer> getFeatureValue(String valueType) {
        return featureValuesMap.get(valueType.toLowerCase());
    }

    public Map<Integer, Boolean> getAvailabilities(String featureType) {
        return featureAvailabilitiesMap.get(featureType.toLowerCase());
    }

    public int getIntValue(String valueType) {
        return intValuesMap.get(valueType.toLowerCase());
    }

    public EndConditionType getEndCondition() {
        return endCondition;
    }

    public ScoringRuleType getScoringRule() {
        return scoringRule;
    }

    public Map<Integer, Integer[]> getAttackerSpecificFlow() {
        return attackerSpecificFlow;
    }

    @Override
    public void addID(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setVal(String type, Object object) {
        try {
            this.getClass().getDeclaredMethod(myResources.getString(type).split(DELIMITER)[0], Object.class).invoke(this, object);
            initUtilMaps();
        } catch (Exception e) {
            // Exception Handling Complete
            new Alert(Alert.AlertType.ERROR, errorMessages.getString(METHOD_INVOCATION_ERROR)).showAndWait();
        }
    }

    public Object getVal(String type) {
        Object output = null;
        try {
            output = this.getClass().getDeclaredMethod(myResources.getString(type).split(DELIMITER)[1]).invoke(this);
        } catch (Exception e) {
            // Exception Handling Complete
            new Alert(Alert.AlertType.ERROR, errorMessages.getString(METHOD_INVOCATION_ERROR)).showAndWait();
        }
        return output;
    }

    private Object getLevelNameElement() {
        return this.name;
    }

    private void setLevelNameElement(Object object) {
        this.name = (String) object;
    }

    private Object getDefenderAvailabilityElement() {
        return this.defenderAvailabilities;
    }

    private void setDefenderAvailabilityElement(Object object) {
        this.defenderAvailabilities = (Map<Integer, Boolean>) object;
    }

    private Object getObstacleAvailabilityElement() {
        return this.obstacleAvailabilities;
    }

    private void setObstacleAvailabilityElement(Object object) {
        this.obstacleAvailabilities = (Map<Integer, Boolean>) object;
    }

    private Object getAttackerAvailabilityElement() {
        return this.attackerAvailabilities;
    }

    private void setAttackerAvailabilityElement(Object object) {
        this.attackerAvailabilities = (Map<Integer, Boolean>) object;
    }

    private Object getDefendersCostElement() {
        return defenderCosts;
    }

    private void setDefendersCostElement(Object object) {
        this.defenderCosts = (Map<Integer, Integer>) object;
    }

    private Object getObstaclesCostElement() {
        return obstacleCosts;
    }

    private void setObstaclesCostElement(Object object) {
        this.obstacleCosts = (Map<Integer, Integer>) object;
    }

    private Object getMapSelectorElement() {
        return this.mapID;
    }

    private void setMapSelectorElement(Object object) {
        this.mapID = (Integer) object;
    }

    private Object getIncomeElement() {
        Map<Integer, Integer> outputMap = new HashMap<>(attackerIncomes);
        outputMap.put(LevelBuildingConstants.TIME_INCOME_ID, timeIncome);
        return outputMap;
    }

    private void setIncomeElement(Object object) {
        Map<Integer, Integer> incomes = (Map<Integer, Integer>) object;
        timeIncome = incomes.remove(LevelBuildingConstants.TIME_INCOME_ID);
        attackerIncomes = incomes;
    }

    private Object getLevelRulesElement() {
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put(LevelBuildingConstants.SCORING_RULE, scoringRule.toString());
        outputMap.put(LevelBuildingConstants.END_CONDITION, endCondition.toString());
        outputMap.put(LevelBuildingConstants.STARTING_MONEY, startingMoney);
        outputMap.put(LevelBuildingConstants.STARTING_LIVES, startingLives);
        outputMap.put(LevelBuildingConstants.ATTACKER_FLOW_TIME, gameTime);
        return outputMap;
    }

    private void setLevelRulesElement(Object object) {
        Map<String, Object> inputMap = (Map<String, Object>) object;
        scoringRule = ScoringRuleType.valueOf((String) inputMap.get(LevelBuildingConstants.SCORING_RULE));
        endCondition = EndConditionType.valueOf((String) inputMap.get(LevelBuildingConstants.END_CONDITION));
        startingMoney = (Integer) inputMap.get(LevelBuildingConstants.STARTING_MONEY);
        startingLives = (Integer) inputMap.get(LevelBuildingConstants.STARTING_LIVES);
        gameTime = (Integer) inputMap.get(LevelBuildingConstants.ATTACKER_FLOW_TIME);
    }

    private Object getAttackerValueElement() {
        return attackerValues;
    }

    private void setAttackerValueElement(Object object) {
        this.attackerValues = (Map<Integer, Integer>) object;
    }

    private Object getAttackerFlowElement() {
        Map<Integer, Map<String, Object>> dataMap = new HashMap<>();
        attackerSpecificFlow.forEach((id, specificFlow) -> {
            dataMap.put(id, new HashMap<>());
            dataMap.get(id).put(AttackerFlowType.TIME_SPECIFIC.toString(), specificFlow);
        });
        attackerFlowMultiplier.forEach((id, flowMultiplier) -> dataMap.get(id).put(LevelBuildingConstants.FLOW_MULTIPLIER, flowMultiplier));
        return dataMap;
    }

    private void setAttackerFlowElement(Object object) {
        Map<Integer, Map<String, Object>> dataMap = (Map<Integer, Map<String, Object>>) object;
        dataMap.forEach((id, singleDataMap) -> {
            attackerSpecificFlow.put(id, (Integer[]) singleDataMap.get(AttackerFlowType.TIME_SPECIFIC.toString()));
            attackerFlowMultiplier.put(id, (Integer) singleDataMap.get(LevelBuildingConstants.FLOW_MULTIPLIER));
        });
    }

    private void initPrivateMaps() {
        this.defenderCosts = new HashMap<>();
        this.obstacleCosts = new HashMap<>();
        this.attackerValues = new HashMap<>();
        this.attackerIncomes = new HashMap<>();
        this.defenderAvailabilities = new HashMap<>();
        this.attackerAvailabilities = new HashMap<>();
        this.obstacleAvailabilities = new HashMap<>();
        this.attackerSpecificFlow = new HashMap<>();
        this.attackerFlowMultiplier = new HashMap<>();
        initUtilMaps();
    }

    private void initUtilMaps() {
        this.featureAvailabilitiesMap = new HashMap<>() {{
            put(DEFENDER, defenderAvailabilities);
            put(ATTACKER, attackerAvailabilities);
            put(OBSTACLE, obstacleAvailabilities);
        }};
        this.featureValuesMap = new HashMap<>() {{
            put(DEFENDER_COSTS, defenderCosts);
            put(ATTACKER_VALUES, attackerValues);
            put(OBSTACLE_COSTS, obstacleCosts);
            put(ATTACKER_INCOMES, attackerIncomes);
            put(ATTACKER_FLOW_MULTIPLIER, attackerFlowMultiplier);
        }};
        this.intValuesMap = new HashMap<>() {{
            put(MAP_ID, mapID);
            put(TIME_INCOME, timeIncome);
            put(STARTING_LIVES, startingLives);
            put(STARTING_MONEY, startingMoney);
            put(GAME_TIME, gameTime);
        }};
    }
}
