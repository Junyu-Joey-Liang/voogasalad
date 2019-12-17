package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.attackerflow;

import javafx.scene.Node;
import voogasalad.authoring.authoringfeature.Attacker;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.enumtypes.EndConditionType;
import voogasalad.authoring.controller.levelbuilder.LevelBuildingConstants;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.LevelBuilderElement;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElementObserver;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.levelrules.LevelRulesElementObserver;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AttackerFlowElement implements LevelBuilderElement, AbstractAvailabilityElementObserver, AttackerFlowElementViewObserver, LevelRulesElementObserver {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String ATTACKER_FLOW_NAME = "attackerFlowName";
    private static final int CONSTANT_MULTIPLIER = 100;
    private static final int DEFAULT_MAX_TIME = 100;

    private ResourceBundle displayInfo;
    private AttackerFlowElementView myView;
    private Map<Integer, Attacker> attackerFeatures;
    private Map<Integer, Integer[]> attackerSpecificFlow;

    private int maxTime;

    public AttackerFlowElement() {
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        myView = new AttackerFlowElementView(this);
        attackerFeatures = new HashMap<>();
        attackerSpecificFlow = new HashMap<>();
        maxTime = DEFAULT_MAX_TIME;
    }

    @Override
    public Object getVal() {
        Map<Integer, Map<String, Object>> dataMap = new HashMap<>();
        attackerSpecificFlow.forEach((ID, specificFlow) -> {
            dataMap.put(ID, new HashMap<>());
            dataMap.get(ID).put(LevelBuildingConstants.TIME_SPECIFIC_FLOW, deepCopyArray(attackerSpecificFlow.get(ID)));
            dataMap.get(ID).put(LevelBuildingConstants.FLOW_MULTIPLIER, myView.getFlowMultiplier(ID));
        });
        return dataMap;
    }

    @Override
    public void setVal(Object object) {
        Map<Integer, Map<String, Object>> dataMap = (Map<Integer, Map<String, Object>>) object;
        dataMap.forEach((id, singleDataMap) -> {
            Integer[] specificFlow = deepCopyArray((Integer[]) singleDataMap.get(LevelBuildingConstants.TIME_SPECIFIC_FLOW));
            int ID = id;
            updateLevelTime(specificFlow.length - 1);
            attackerSpecificFlow.put(ID, specificFlow);
            myView.updateFlowMultiplier(ID, (int) singleDataMap.get(LevelBuildingConstants.FLOW_MULTIPLIER));
            myView.updateMaxTime(ID, specificFlow.length - 1);
            myView.updateSpecificFlow(ID, myView.getCurrentTime(ID), specificFlow[myView.getCurrentTime(ID)]);
        });
    }

    @Override
    public void addObservers(Object... objects) {
        //Do nothing
    }

    @Override
    public void updateElement(Set<AuthoringFeature> authoringFeatures) {
        Set<Attacker> attackersSet = new HashSet<>();
        authoringFeatures.forEach(attacker -> attackersSet.add((Attacker) attacker));
        attackersSet.forEach(attacker -> {
            if (attackerFeatures.containsKey(attacker.getID())) {
                myView.updateAttacker(attacker.getID(), attacker.getName(), attacker.getImagePath());
            } else {
                Integer[] flowArray = new Integer[maxTime + 1];
                Arrays.fill(flowArray, 0);
                attackerSpecificFlow.put(attacker.getID(), flowArray);
                myView.addAttacker(attacker.getID(), attacker.getName(), attacker.getImagePath());
            }
            attackerFeatures.put(attacker.getID(), attacker);
        });
    }

    @Override
    public Node getNode() {
        return myView.getNode();
    }

    @Override
    public String getName() {
        return displayInfo.getString(ATTACKER_FLOW_NAME);
    }

    @Override
    public void updateAvailability(Map<Integer, Boolean> availabilityMap) {
        myView.updateDisplayedAttackers(availabilityMap);
    }

    @Override
    public void updateTimeChange(int ID, int newTime) {
        myView.updateSpecificFlow(ID, newTime, attackerSpecificFlow.get(ID)[newTime]);
    }

    @Override
    public void updateNumberChange(int ID, int time, int newNumber) {
        attackerSpecificFlow.get(ID)[time] = newNumber;
    }

    @Override
    public void updateEndCondition(EndConditionType endCondition) {
        if (endCondition.equals(EndConditionType.INFINITE)) {
            attackerFeatures.keySet().forEach(id -> myView.showMultiplier(id));
        } else {
            attackerFeatures.keySet().forEach(id -> {
                myView.hideMultiplier(id);
                myView.updateFlowMultiplier(id, CONSTANT_MULTIPLIER);
            });
        }
    }

    @Override
    public void updateLevelTime(int levelTime) {
        int oldTime = maxTime;
        maxTime = levelTime;
        myView.updateMaxTime(maxTime);
        attackerSpecificFlow.forEach((id, specificFlow) -> {
            Integer[] newSpecificFlow = new Integer[maxTime + 1];
            Arrays.fill(newSpecificFlow, 0);
            for (int currentTime = 0; currentTime <= maxTime; currentTime++) {
                if (currentTime <= oldTime) {
                    newSpecificFlow[currentTime] = specificFlow[currentTime];
                }
            }
            attackerSpecificFlow.put(id, newSpecificFlow);
            myView.updateMaxTime(id, maxTime);
        });
    }

    private Integer[] deepCopyArray(Integer[] originalArray) {
        Integer[] deepCopy = new Integer[originalArray.length];
        for (int i = 0; i < deepCopy.length; i++) {
            deepCopy[i] = Integer.valueOf(originalArray[i]);
        }
        return deepCopy;
    }
}
