package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.levelname;

import javafx.scene.Node;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.LevelBuilderElement;

import java.util.ResourceBundle;
import java.util.Set;

public class LevelNameElement implements LevelBuilderElement {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String LEVEL_NAME = "levelName";

    private ResourceBundle displayInfo;
    private LevelNameElementView myView;

    public LevelNameElement() {
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        myView = new LevelNameElementView();
    }

    @Override
    public Object getVal() {
        return myView.getName();
    }

    @Override
    public void setVal(Object object) {
        myView.setName((String) object);
    }

    @Override
    public void addObservers(Object... objects) {
        //Do nothing
    }

    @Override
    public void updateElement(Set<AuthoringFeature> authoringFeatures) {
        //Do nothing
    }

    @Override
    public Node getNode() {
        return myView.getNode();
    }

    @Override
    public String getName() {
        return displayInfo.getString(LEVEL_NAME);
    }
}
