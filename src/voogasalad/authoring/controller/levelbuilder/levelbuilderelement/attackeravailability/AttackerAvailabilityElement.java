package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.attackeravailability;

import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElement;

import java.util.ResourceBundle;

public class AttackerAvailabilityElement extends AbstractAvailabilityElement {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String ELEMENT_NAME = "attackerAvailabilityName";
    private ResourceBundle displayInfo;

    public AttackerAvailabilityElement() {
        super();
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        super.setView(AttackerAvailabilityElementView.class);
    }

    @Override
    public String getName() {
        return displayInfo.getString(ELEMENT_NAME);
    }
}
