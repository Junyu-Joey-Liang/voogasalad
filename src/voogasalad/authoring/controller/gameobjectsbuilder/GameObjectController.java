package voogasalad.authoring.controller.gameobjectsbuilder;

import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.authoringfeature.Projectile;

import java.util.Set;

public interface GameObjectController {
    Set<Object> getObservers();
    void updateObservers();
    String getType();
    AbstractAttributePaneManager createCentralPane();
    AbstractFeature createNewObj();
    String getDefaultName();
}
