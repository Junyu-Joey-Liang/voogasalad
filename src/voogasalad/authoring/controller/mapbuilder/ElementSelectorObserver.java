package voogasalad.authoring.controller.mapbuilder;

import javafx.scene.image.Image;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;

public interface ElementSelectorObserver {
    void updateSelected(MapElementType elementType);

    void updateImageChange(MapElementType elementType, Image image);
}
