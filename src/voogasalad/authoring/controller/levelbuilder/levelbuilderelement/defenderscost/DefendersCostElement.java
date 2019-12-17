package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.defenderscost;

import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeaturevalue.AbstractFeatureValueElement;

import java.util.ResourceBundle;

public class DefendersCostElement extends AbstractFeatureValueElement {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String DEFENDER_COST = "defenderCostLabel";
    public static final String DEFENDER_COST_NAME = "defenderCostName";

    private ResourceBundle displayInfo;

    public DefendersCostElement() {
        super();
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        super.setView(displayInfo.getString(DEFENDER_COST));
    }

    @Override
    public String getName() {
        return displayInfo.getString(DEFENDER_COST_NAME);
    }
}
