package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.mapselector;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import voogasalad.authoring.controller.Viewable;
import voogasalad.authoring.util.elementbuilders.ToggleGroupInputElement;

import java.util.ResourceBundle;

public class MapSelectorElementView implements Viewable {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String LABEL_NAME = "mapSelectorLabel";
    public static final int NODE_HEIGHT = 100;

    private ScrollPane mainNode;
    private ToggleGroupInputElement mapToggleGroup;


    public MapSelectorElementView() {
        ResourceBundle displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        mapToggleGroup = new ToggleGroupInputElement(displayInfo.getString(LABEL_NAME));
        mainNode = new ScrollPane(mapToggleGroup.getNode());
        mainNode.setPrefViewportHeight(NODE_HEIGHT);
    }

    public void addMap(String name) {
        mapToggleGroup.addToggle(name);
    }

    public String getVal() {
        return mapToggleGroup.getVal();
    }

    public void setVal(String name) {
        mapToggleGroup.setVal(name);
    }

    @Override
    public Node getNode() {
        return mainNode;
    }
}
