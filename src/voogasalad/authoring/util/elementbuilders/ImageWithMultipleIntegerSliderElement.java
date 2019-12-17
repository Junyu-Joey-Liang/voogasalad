package voogasalad.authoring.util.elementbuilders;

import javafx.scene.Node;
import javafx.scene.layout.VBox;
import voogasalad.authoring.util.elementbuilders.resources.ElementBuilderConstants;

public class ImageWithMultipleIntegerSliderElement extends MultipleIntegerSliderElement {
    private static final String CSS_TAG = "image-with-multiple-slider-element";
    private VBox mainNode;
    private LabelledImageElement labelledImageElement;

    public ImageWithMultipleIntegerSliderElement(String name, String imagePath, int imageSize, int sliderPrefWidth) {
        super(sliderPrefWidth);
        mainNode = new VBox();
        mainNode.getStylesheets().add(getClass().getResource(ElementBuilderConstants.ELEMENT_BUILDER_CSS_FILE).toExternalForm());
        mainNode.getStyleClass().add(CSS_TAG);
        labelledImageElement = new LabelledImageElement(name, imagePath, imageSize);
        mainNode.getChildren().addAll(labelledImageElement.getNode(), super.getNode());
    }

    public void setName(String name) {
        labelledImageElement.setName(name);
    }

    public void setImage(String imagePath) {
        labelledImageElement.setImage(imagePath);
    }

    public Node getImageNode() {
        return labelledImageElement.getNode();
    }

    public Node getSlidersNode() {
        return super.getNode();
    }

    @Override
    public Node getNode() {
        return mainNode;
    }
}
