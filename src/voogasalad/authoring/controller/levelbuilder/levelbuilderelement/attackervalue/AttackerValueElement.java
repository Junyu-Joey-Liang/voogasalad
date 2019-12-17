package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.attackervalue;

import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeaturevalue.AbstractFeatureValueElement;

import java.util.ResourceBundle;

public class AttackerValueElement extends AbstractFeatureValueElement {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String ATTACKER_VALUE = "attackerValueLabel";
    public static final String ATTACKER_VALUE_NAME = "attackerValueName";

    private ResourceBundle displayInfo;

    public AttackerValueElement() {
        super();
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        super.setView(displayInfo.getString(ATTACKER_VALUE));
    }

    @Override
    public String getName() {
        return displayInfo.getString(ATTACKER_VALUE_NAME);
    }
}
