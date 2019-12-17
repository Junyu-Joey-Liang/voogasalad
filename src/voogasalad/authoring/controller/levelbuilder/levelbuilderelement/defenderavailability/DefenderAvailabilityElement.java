package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.defenderavailability;

import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElement;

import java.util.ResourceBundle;

public class DefenderAvailabilityElement extends AbstractAvailabilityElement {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String ELEMENT_NAME = "defenderAvailabilityName";
    private ResourceBundle displayInfo;

    public DefenderAvailabilityElement() {
        super();
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        super.setView(DefenderAvailabilityElementView.class);
    }

    @Override
    public String getName() {
        return displayInfo.getString(ELEMENT_NAME);
    }
}
