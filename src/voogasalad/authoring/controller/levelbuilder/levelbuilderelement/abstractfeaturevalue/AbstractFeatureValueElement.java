package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeaturevalue;

import javafx.scene.Node;
import voogasalad.authoring.authoringfeature.AbstractFeature;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.LevelBuilderElement;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.abstractfeatureavailability.AbstractAvailabilityElementObserver;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractFeatureValueElement implements LevelBuilderElement, AbstractAvailabilityElementObserver {
    private AbstractFeatureValueElementView myView;
    private Map<Integer, AbstractFeature> abstractFeatures;

    public AbstractFeatureValueElement() {
        abstractFeatures = new HashMap<>();
    }

    public void setView(String valueName) {
        myView = new AbstractFeatureValueElementView(valueName);
    }

    @Override
    public Object getVal() {
        Map<Integer, Integer> featureValues = new HashMap<>();
        abstractFeatures.keySet().forEach(ID -> featureValues.put(ID, myView.getAbstractFeatureValue(ID)));
        return featureValues;
    }

    @Override
    public void setVal(Object object) {
        Map<Integer, Integer> featureValues = (Map<Integer, Integer>) object;
        featureValues.forEach((key, value) -> myView.setAbstractFeatureValue(key, value));
    }

    @Override
    public void addObservers(Object... objects) {
        //Do nothing
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
    public Node getNode() {
        return myView.getNode();
    }

    @Override
    public abstract String getName();

    @Override
    public void updateAvailability(Map<Integer, Boolean> availabilityMap) {
        myView.updateDisplayedAbstractFeatures(availabilityMap);
    }
}
