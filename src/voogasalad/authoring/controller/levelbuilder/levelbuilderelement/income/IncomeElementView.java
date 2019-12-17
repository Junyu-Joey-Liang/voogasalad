package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.income;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import voogasalad.authoring.controller.Viewable;
import voogasalad.authoring.controller.levelbuilder.LevelBuildingConstants;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeaturevalue.AbstractFeatureValueElementView;
import voogasalad.authoring.util.elementbuilders.InputIntegerSliderElement;

import java.util.Map;
import java.util.ResourceBundle;

public class IncomeElementView implements Viewable {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String ATTACKER_INCOME_LABEL = "attackerIncomeLabel";
    public static final String TIME_INCOME_LABEL = "timeIncomeLabel";
    public static final int MIN_VAL = 0;
    public static final int MAX_INCOME = 100;

    private VBox mainNode;
    private InputIntegerSliderElement timeIncome;
    private AbstractFeatureValueElementView attackerIncomesView;

    public IncomeElementView() {
        ResourceBundle displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        mainNode = new VBox();
        attackerIncomesView = new AbstractFeatureValueElementView(displayInfo.getString(ATTACKER_INCOME_LABEL));
        timeIncome = new InputIntegerSliderElement(displayInfo.getString(TIME_INCOME_LABEL), MIN_VAL, MAX_INCOME, MIN_VAL, null);
        mainNode.getChildren().addAll(timeIncome.getNode(), attackerIncomesView.getNode());
    }

    public void updateAttacker(int ID, String name, String imagePath) {
        attackerIncomesView.updateAbstractFeature(ID, name, imagePath);
    }

    public void addAttacker(int ID, String name, String imagePath) {
        attackerIncomesView.addAbstractFeature(ID, name, imagePath);
    }

    public int getVal(int ID) {
        if (ID == LevelBuildingConstants.TIME_INCOME_ID) {
            return timeIncome.getVal();
        } else {
            return attackerIncomesView.getAbstractFeatureValue(ID);
        }
    }

    public void setTimeIncome(int reward) {
        timeIncome.setVal(reward);
    }

    public void setAttackerIncome(int ID, int reward) {
        attackerIncomesView.setAbstractFeatureValue(ID, reward);
    }

    public void updateDisplayedAttackers(Map<Integer, Boolean> availabilityMap) {
        attackerIncomesView.updateDisplayedAbstractFeatures(availabilityMap);
    }

    @Override
    public Node getNode() {
        return mainNode;
    }
}