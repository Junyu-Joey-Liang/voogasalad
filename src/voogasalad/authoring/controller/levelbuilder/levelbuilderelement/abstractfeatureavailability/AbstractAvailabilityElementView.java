package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import voogasalad.authoring.controller.Viewable;
import voogasalad.authoring.util.elementbuilders.ImageWithCheckBoxElement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractAvailabilityElementView implements Viewable {
    public static final int IMAGE_SIZE = 50;
    public static final int NODE_HEIGHT = 100;
    private Set<AbstractAvailabilityElementViewObserver> observers;

    private HBox featuresBox;
    private ScrollPane featuresScrollPane;
    private Map<Integer, ImageWithCheckBoxElement> featureElements;

    public AbstractAvailabilityElementView(AbstractAvailabilityElementViewObserver observer) {
        observers = new HashSet<>();
        observers.add(observer);

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
        ImageWithCheckBoxElement featureElement = featureElements.get(ID);
        featureElement.setName(name);
        featureElement.setImage(imagePath);
    }

    public void addAbstractFeature(int ID, String name, String imagePath, String labelName) {
        ImageWithCheckBoxElement newElement = new ImageWithCheckBoxElement(name, imagePath, IMAGE_SIZE, labelName, ((observable, oldValue, newValue) -> updateAvailability()));
        featuresBox.getChildren().add(newElement.getNode());
        featureElements.put(ID, newElement);
    }

    public abstract void addAbstractFeature(int ID, String name, String imagePath);

    public boolean getAbstractFeatureAvailability(int ID) {
        return featureElements.get(ID).getSelected();
    }

    public void setAbstractFeatureAvailability(int ID, boolean availability) {
        featureElements.get(ID).setSelected(availability);
    }

    private void updateAvailability() {
        Map<Integer, Boolean> availabilityMap = new HashMap<>();
        featureElements.forEach((id, element) -> availabilityMap.put(id, element.getSelected()));
        updateObservers(availabilityMap);
    }

    private void updateObservers(Map<Integer, Boolean> availabilityMap) {
        observers.forEach(observer -> observer.updateAvailableAbstractFeatures(availabilityMap));
    }
}
