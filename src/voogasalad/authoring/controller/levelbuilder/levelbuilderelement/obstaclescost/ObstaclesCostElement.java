package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.obstaclescost;

import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeaturevalue.AbstractFeatureValueElement;

import java.util.ResourceBundle;

public class ObstaclesCostElement extends AbstractFeatureValueElement {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String OBSTACLE_COST = "obstacleCost";
    public static final String ELEMENT_NAME = "obstacleName";
    private ResourceBundle displayInfo;

    public ObstaclesCostElement() {
        super();
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        super.setView(displayInfo.getString(OBSTACLE_COST));
    }

    @Override
    public String getName() {
        return displayInfo.getString(ELEMENT_NAME);
    }
}
