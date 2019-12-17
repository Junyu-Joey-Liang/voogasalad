package voogasalad.authoring.util.elementbuilders;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import voogasalad.authoring.util.elementbuilders.resources.ElementBuilderConstants;


public class ImageWithIntegerSliderElement {
    private static final String CSS_TAG = "image-with-slider-element";
    private VBox mainNode;
    private LabelledImageElement labelledImageElement;
    private InputIntegerSliderElement inputIntegerSliderElement;

    public ImageWithIntegerSliderElement(String name, String imagePath, int imageSize) {
        mainNode = new VBox();
        mainNode.getStylesheets().add(getClass().getResource(ElementBuilderConstants.ELEMENT_BUILDER_CSS_FILE).toExternalForm());
        mainNode.getStyleClass().add(CSS_TAG);
        labelledImageElement = new LabelledImageElement(name, imagePath, imageSize);
        mainNode.getChildren().add(labelledImageElement.getNode());
    }

    public void setSlider(String sliderLabel, int sliderMinVal, int sliderMaxVal, int sliderDefaultVal, ChangeListener listener, int sliderPrefWidth) {
        inputIntegerSliderElement = new InputIntegerSliderElement(sliderLabel, sliderMinVal, sliderMaxVal, sliderDefaultVal, listener, sliderPrefWidth);
        mainNode.getChildren().add(inputIntegerSliderElement.getNode());
    }

    public Node getNode() {
        return mainNode;
    }

    public void setName(String name) {
        labelledImageElement.setName(name);
    }

    public void setImage(String imagePath) {
        labelledImageElement.setImage(imagePath);
    }

    public int getVal() {
        return inputIntegerSliderElement.getVal();
    }

    public void setVal(int val) {
        inputIntegerSliderElement.setVal(val);
    }
}
