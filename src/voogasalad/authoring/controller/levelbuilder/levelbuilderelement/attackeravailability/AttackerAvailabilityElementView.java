package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.attackeravailability;

import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElementView;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElementViewObserver;

import java.util.ResourceBundle;

public class AttackerAvailabilityElementView extends AbstractAvailabilityElementView {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String ATTACKER_AVAILABILITY = "attackerAvailabilityLabel";
    private ResourceBundle displayInfo;

    public AttackerAvailabilityElementView(AbstractAvailabilityElementViewObserver observer) {
        super(observer);
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
    }

    @Override
    public void addAbstractFeature(int ID, String name, String imagePath) {
        super.addAbstractFeature(ID, name, imagePath, displayInfo.getString(ATTACKER_AVAILABILITY));
    }
}
