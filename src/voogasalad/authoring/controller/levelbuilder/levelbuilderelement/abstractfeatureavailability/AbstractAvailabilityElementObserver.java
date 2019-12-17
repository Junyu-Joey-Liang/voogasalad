package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability;

import java.util.Map;

public interface AbstractAvailabilityElementObserver {
    void updateAvailability(Map<Integer, Boolean> availabilityMap);

}
