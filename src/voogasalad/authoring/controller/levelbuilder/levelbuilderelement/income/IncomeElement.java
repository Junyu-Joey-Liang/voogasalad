package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.income;

import javafx.scene.Node;
import voogasalad.authoring.authoringfeature.Attacker;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.controller.levelbuilder.LevelBuildingConstants;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.LevelBuilderElement;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElementObserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class IncomeElement implements LevelBuilderElement, AbstractAvailabilityElementObserver {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String INCOME_ELEMENT_NAME = "incomeElementName";

    private ResourceBundle displayInfo;
    private IncomeElementView myView;
    private Map<Integer, Attacker> attackerFeatures;

    public IncomeElement() {
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        myView = new IncomeElementView();
        attackerFeatures = new HashMap<>();
    }

    @Override
    public Object getVal() {
        Map<Integer, Integer> income = new HashMap<>();
        Set<Integer> idSet = new HashSet<>(attackerFeatures.keySet());
        idSet.add(-1);
        idSet.forEach(ID -> income.put(ID, myView.getVal(ID)));
        return income;
    }

    @Override
    public void setVal(Object object) {
        Map<Integer, Integer> attackerRewards = (Map<Integer, Integer>) object;
        attackerRewards.entrySet().stream().filter(entry -> entry.getKey().equals(LevelBuildingConstants.TIME_INCOME_ID)).forEach(entry -> myView.setTimeIncome(entry.getValue()));
        attackerRewards.entrySet().stream().filter(entry -> !entry.getKey().equals(LevelBuildingConstants.TIME_INCOME_ID)).forEach(entry -> myView.setAttackerIncome(entry.getKey(), entry.getValue()));
    }

    @Override
    public void addObservers(Object... objects) {
        // Do nothing
    }

    @Override
    public void updateElement(Set<AuthoringFeature> authoringFeatures) {
        Set<Attacker> attackersSet = new HashSet<>();
        authoringFeatures.forEach(attacker -> attackersSet.add((Attacker) attacker));
        attackersSet.forEach(attacker -> {
            if (attackerFeatures.containsKey(attacker.getID())) {
                myView.updateAttacker(attacker.getID(), attacker.getName(), attacker.getImagePath());
            } else {
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
        return displayInfo.getString(INCOME_ELEMENT_NAME);
    }

    @Override
    public void updateAvailability(Map<Integer, Boolean> availabilityMap) {
        myView.updateDisplayedAttackers(availabilityMap);
    }
}
