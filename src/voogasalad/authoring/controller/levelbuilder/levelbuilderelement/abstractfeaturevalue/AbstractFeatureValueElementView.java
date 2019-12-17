package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeaturevalue;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import voogasalad.authoring.controller.Viewable;
import voogasalad.authoring.util.elementbuilders.ImageWithIntegerSliderElement;

import java.util.HashMap;
import java.util.Map;

public class AbstractFeatureValueElementView implements Viewable {
    private static final int NODE_HEIGHT = 105;
    private static final int IMAGE_SIZE = 50;
    private static final int MIN_VAL = 0;
    private static final int MAX_VAL = 500;
    private static final int SLIDER_PREF_WIDTH = 120;

    private HBox featuresBox;
    private ScrollPane featuresScrollPane;
    private Map<Integer, ImageWithIntegerSliderElement> featureElements;
    private String valueName;

    public AbstractFeatureValueElementView(String valueName) {
        this.valueName = valueName;
        featuresBox = new HBox();
        featuresScrollPane = new ScrollPane(featuresBox);
        featuresScrollPane.setPrefViewportHeight(NODE_HEIGHT);
        featureElements = new HashMap<>();
    }

    @Override
    public Node getNode() {
        return featuresScrollPane;
    }

    public void updateAbstractFeature(int ID, String name, String imagePath) {
        ImageWithIntegerSliderElement featureElement = featureElements.get(ID);
        featureElement.setName(name);
        featureElement.setImage(imagePath);
    }

    public void addAbstractFeature(int ID, String name, String imagePath) {
        ImageWithIntegerSliderElement newElement = new ImageWithIntegerSliderElement(name, imagePath, IMAGE_SIZE);
        newElement.setSlider(valueName, MIN_VAL, MAX_VAL, MIN_VAL, null, SLIDER_PREF_WIDTH);
        featuresBox.getChildren().add(newElement.getNode());
        featureElements.put(ID, newElement);
    }

    public void setAbstractFeatureValue(int ID, int cost) {
        featureElements.get(ID).setVal(cost);
    }

    public int getAbstractFeatureValue(int ID) {
        return featureElements.get(ID).getVal();
    }

    public void updateDisplayedAbstractFeatures(Map<Integer, Boolean> availabilityMap) {
        availabilityMap.forEach((ID, bool) -> {
            if (!bool && featuresBox.getChildren().contains(featureElements.get(ID).getNode())) {
                featuresBox.getChildren().remove(featureElements.get(ID).getNode());
            } else if (bool && !featuresBox.getChildren().contains(featureElements.get(ID).getNode())) {
                featuresBox.getChildren().add(featureElements.get(ID).getNode());
            }
        });
    }
}
