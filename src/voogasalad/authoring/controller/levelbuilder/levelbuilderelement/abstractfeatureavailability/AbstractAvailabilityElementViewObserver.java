package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability;

import java.util.Map;

public interface AbstractAvailabilityElementViewObserver {
    void updateAvailableAbstractFeatures(Map<Integer, Boolean> availabilityMap);
}
