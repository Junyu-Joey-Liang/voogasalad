package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.obstacleavailability;

import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElement;

import java.util.ResourceBundle;

public class ObstacleAvailabilityElement extends AbstractAvailabilityElement {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String ELEMENT_NAME = "obstacleAvailabilityName";
    private ResourceBundle displayInfo;

    public ObstacleAvailabilityElement() {
        super();
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        super.setView(ObstacleAvailabilityElementView.class);
    }

    @Override
    public String getName() {
        return displayInfo.getString(ELEMENT_NAME);
    }
}
