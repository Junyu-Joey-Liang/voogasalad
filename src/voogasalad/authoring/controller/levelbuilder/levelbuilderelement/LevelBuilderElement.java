package voogasalad.authoring.controller.levelbuilder.levelbuilderelement;

import javafx.scene.Node;
import voogasalad.authoring.authoringfeature.AuthoringFeature;

import java.util.Set;

public interface LevelBuilderElement {
    Object getVal();

    void setVal(Object object);

    void addObservers(Object... objects);

    void updateElement(Set<AuthoringFeature> authoringFeatures);

    Node getNode();

    String getName();
}
