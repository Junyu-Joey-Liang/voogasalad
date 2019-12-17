package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.defenderavailability;

import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElementView;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElementViewObserver;

import java.util.ResourceBundle;

public class DefenderAvailabilityElementView extends AbstractAvailabilityElementView {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String DEFENDER_AVAILABILITY = "defenderAvailabilityLabel";
    private ResourceBundle displayInfo;

    public DefenderAvailabilityElementView(AbstractAvailabilityElementViewObserver observer) {
        super(observer);
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
    }

    @Override
    public void addAbstractFeature(int ID, String name, String imagePath) {
        super.addAbstractFeature(ID, name, imagePath, displayInfo.getString(DEFENDER_AVAILABILITY));
    }
}
