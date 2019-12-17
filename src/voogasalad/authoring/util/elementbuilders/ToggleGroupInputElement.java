package voogasalad.authoring.util.elementbuilders;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import voogasalad.authoring.util.elementbuilders.resources.ElementBuilderConstants;

public class ToggleGroupInputElement {
    private static final String CSS_TAG = "toggle-group-element";
    private static final String VBOX_CSS_TAG = "toggle-group-vbox";
    private ToggleGroup toggleGroup;
    private HBox mainNode;
    private VBox togglesNode;

    public ToggleGroupInputElement(String labelName, ChangeListener listener) {
        mainNode = new HBox();
        mainNode.getStylesheets().add(getClass().getResource(ElementBuilderConstants.ELEMENT_BUILDER_CSS_FILE).toExternalForm());
        mainNode.getStyleClass().add(CSS_TAG);
        togglesNode = new VBox();
        togglesNode.getStyleClass().add(VBOX_CSS_TAG);
        toggleGroup = new ToggleGroup();
        if (listener != null) {
            toggleGroup.selectedToggleProperty().addListener(listener);
        }
        Label label = new Label(labelName);
        mainNode.getChildren().addAll(label, togglesNode);
    }

    public ToggleGroupInputElement(String labelName) {
        this(labelName, null);
    }

    public void addToggle(String name) {
        RadioButton newButton = new RadioButton(name);
        newButton.setMnemonicParsing(false);
        newButton.setToggleGroup(toggleGroup);
        togglesNode.getChildren().add(newButton);
    }

    public String getVal() {
        if (toggleGroup.getSelectedToggle() == null) {
            throw new ToggleSelectionException();
        }
        return ((RadioButton) toggleGroup.getSelectedToggle()).getText();
    }

    public void setVal(String name) {
        toggleGroup.getToggles().stream().filter(toggle -> ((RadioButton) toggle).getText().equals(name)).findFirst().get().setSelected(true);
    }

    public void setUnselectable(boolean bool) {
        toggleGroup.getToggles().forEach(toggle -> {
            ((Node) toggle).setDisable(bool);
        });
    }

    public Node getNode() {
        return mainNode;
    }
}
