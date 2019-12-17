package voogasalad.authoring.util.elementbuilders;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import voogasalad.authoring.util.elementbuilders.resources.ElementBuilderConstants;

public class ImageWithToggleAndSlidersElement extends ImageWithMultipleIntegerSliderElement {
    private static final String CSS_TAG = "image-with-toggle-and-slider-element";
    private VBox mainNode;
    private ToggleGroupInputElement toggleGroupInputElement;

    public ImageWithToggleAndSlidersElement(String name, String imagePath, int imageSize, int sliderPrefWidth, String toggleLabelName, ChangeListener toggleListener) {
        super(name, imagePath, imageSize, sliderPrefWidth);
        toggleGroupInputElement = new ToggleGroupInputElement(toggleLabelName, toggleListener);
        mainNode = new VBox();
        mainNode.getStylesheets().add(getClass().getResource(ElementBuilderConstants.ELEMENT_BUILDER_CSS_FILE).toExternalForm());
        mainNode.getStyleClass().add(CSS_TAG);
        mainNode.getChildren().addAll(super.getImageNode(), toggleGroupInputElement.getNode(), super.getSlidersNode());
    }

    public void addToggle(String name) {
        toggleGroupInputElement.addToggle(name);
    }

    public String getToggleVal() {
        return toggleGroupInputElement.getVal();
    }

    public void setToggleVal(String val) {
        toggleGroupInputElement.setVal(val);
    }

    public void setToggleUnselectable(boolean bool) {
        toggleGroupInputElement.setUnselectable(bool);
    }

    @Override
    public Node getNode() {
        return mainNode;
    }
}
