package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.LevelBuilderElement;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public abstract class AbstractAvailabilityElement implements LevelBuilderElement, AbstractAvailabilityElementViewObserver {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String ERROR_CREATE_VIEW = "createViewError";
    private ResourceBundle displayInfo;
    private AbstractAvailabilityElementView myView;
    private Map<Integer, AbstractFeature> abstractFeatures;

    private Set<AbstractAvailabilityElementObserver> observers;

    public AbstractAvailabilityElement() {
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        abstractFeatures = new HashMap<>();
        observers = new HashSet<>();
    }

    public void setView(Class viewClass) {
        try {
            myView = (AbstractAvailabilityElementView) viewClass.getDeclaredConstructor(AbstractAvailabilityElementViewObserver.class).newInstance(this);
        } catch (Exception e) {
            // Exception Handling Complete
            new Alert(Alert.AlertType.ERROR, displayInfo.getString(ERROR_CREATE_VIEW)).showAndWait();
        }
    }

    @Override
    public Object getVal() {
        Map<Integer, Boolean> availabilityMap = new HashMap<>();
        abstractFeatures.keySet().forEach(id -> {
            availabilityMap.put(id, myView.getAbstractFeatureAvailability(id));
        });
        return availabilityMap;
    }

    @Override
    public void setVal(Object object) {
        Map<Integer, Boolean> availabilityMap = (Map<Integer, Boolean>) object;
        availabilityMap.forEach((key, value) -> myView.setAbstractFeatureAvailability(key, value));
    }

    @Override
    public void addObservers(Object... objects) {
        Arrays.stream(objects).forEach(observer -> {
            observers.add((AbstractAvailabilityElementObserver) observer);
        });
    }

    @Override
    public void updateElement(Set<AuthoringFeature> authoringFeatures) {
        Set<AbstractFeature> abstractFeaturesSet = new HashSet<>();
        authoringFeatures.forEach(abstractFeature -> abstractFeaturesSet.add((AbstractFeature) abstractFeature));
        abstractFeaturesSet.forEach(abstractFeature -> {
            if (abstractFeatures.containsKey(abstractFeature.getID())) {
                myView.updateAbstractFeature(abstractFeature.getID(), abstractFeature.getName(), abstractFeature.getImagePath());
            } else {
                myView.addAbstractFeature(abstractFeature.getID(), abstractFeature.getName(), abstractFeature.getImagePath());
            }
            abstractFeatures.put(abstractFeature.getID(), abstractFeature);
        });
    }

    @Override
    public abstract String getName();

    @Override
    public Node getNode() {
        return myView.getNode();
    }

    @Override
    public void updateAvailableAbstractFeatures(Map<Integer, Boolean> availabilityMap) {
        observers.forEach(observer -> observer.updateAvailability(availabilityMap));
    }
}
