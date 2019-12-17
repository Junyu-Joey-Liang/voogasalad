package voogasalad.authoring.util.elementbuilders;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import voogasalad.authoring.controller.Viewable;
import voogasalad.authoring.util.elementbuilders.resources.ElementBuilderConstants;

public class ImageWithCheckBoxElement implements Viewable {
    private static final String CSS_TAG = "image-with-checkbox-element";
    private VBox mainNode;
    private LabelledImageElement labelledImageElement;
    private CheckBox checkBox;

    public ImageWithCheckBoxElement(String name, String imagePath, int imageSize, String checkBoxLabel, ChangeListener listener) {
        mainNode = new VBox();
        mainNode.getStylesheets().add(getClass().getResource(ElementBuilderConstants.ELEMENT_BUILDER_CSS_FILE).toExternalForm());
        mainNode.getStyleClass().add(CSS_TAG);
        labelledImageElement = new LabelledImageElement(name, imagePath, imageSize);
        checkBox = new CheckBox(checkBoxLabel);
        checkBox.setSelected(true);
        checkBox.selectedProperty().addListener(listener);
        mainNode.getChildren().addAll(labelledImageElement.getNode(), checkBox);
    }

    @Override
    public Node getNode() {
        return mainNode;
    }

    public void setName(String name) {
        labelledImageElement.setName(name);
    }

    public void setImage(String imagePath) {
        labelledImageElement.setImage(imagePath);
    }

    public boolean getSelected() {
        return checkBox.isSelected();
    }

    public void setSelected(boolean bool) {
        checkBox.setSelected(bool);
    }
}
