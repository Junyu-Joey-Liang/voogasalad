package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.levelname;

import javafx.scene.Node;
import voogasalad.authoring.controller.Viewable;
import voogasalad.authoring.util.elementbuilders.InputTextElement;

import java.util.ResourceBundle;

public class LevelNameElementView implements Viewable {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String LABEL_NAME = "levelNameLabel";
    public static final String DEFAULT_NAME_TAG = "levelNameDefault";
    private InputTextElement inputTextElement;

    public LevelNameElementView() {
        ResourceBundle displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        this.inputTextElement = new InputTextElement(displayInfo.getString(LABEL_NAME), displayInfo.getString(DEFAULT_NAME_TAG));
    }

    @Override
    public Node getNode() {
        return inputTextElement.getNode();
    }

    public String getName() {
        return inputTextElement.getText();
    }

    public void setName(String name) {
        inputTextElement.setText(name);
    }
}
