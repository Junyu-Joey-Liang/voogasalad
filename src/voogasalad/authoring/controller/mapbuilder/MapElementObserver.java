package voogasalad.authoring.controller.mapbuilder;

import javafx.scene.image.Image;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;

public interface MapElementObserver {
    void updateImageChange(MapElementType elementType, Image image);
}
