package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.mapselector;

import javafx.scene.Node;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.MapFeature;
import voogasalad.authoring.controller.levelbuilder.LevelConfigurationException;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.LevelBuilderElement;
import voogasalad.authoring.util.elementbuilders.ToggleSelectionException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class MapSelectorElement implements LevelBuilderElement {
    public static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    public static final String MAP_SELECTOR_ERROR = "mapSelectorError";
    public static final String MAP_SELECTOR_NAME = "mapSelectorName";

    private ResourceBundle displayInfo;
    private Map<Integer, MapFeature> mapFeatures;
    private MapSelectorElementView myView;

    public MapSelectorElement() {
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        mapFeatures = new HashMap<>();
        myView = new MapSelectorElementView();
    }

    @Override
    public Object getVal() {
        try {
            String selectedMap = myView.getVal();
            return mapFeatures.entrySet().stream().filter(entry -> entry.getValue().getName().equals(selectedMap)).findFirst().get().getKey();
        } catch (ToggleSelectionException e) {
            throw new LevelConfigurationException(e, displayInfo.getString(MAP_SELECTOR_ERROR));
        }
    }

    @Override
    public void setVal(Object object) {
        if (object != null) {
            myView.setVal(mapFeatures.get(object).getName());
        }
    }

    @Override
    public void addObservers(Object... objects) {
        // Do nothing
    }

    @Override
    public void updateElement(Set<AuthoringFeature> authoringFeatures) {
        Set<MapFeature> mapFeaturesSet = new HashSet<>();
        authoringFeatures.forEach(mapFeature -> mapFeaturesSet.add((MapFeature) mapFeature));
        mapFeaturesSet.forEach(mapFeature -> {
            if (!mapFeatures.containsKey(mapFeature.getID())) {
                myView.addMap(mapFeature.getName());
            }
            mapFeatures.put(mapFeature.getID(), mapFeature);
        });
    }

    @Override
    public Node getNode() {
        return myView.getNode();
    }

    @Override
    public String getName() {
        return displayInfo.getString(MAP_SELECTOR_NAME);
    }
}
