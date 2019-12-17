package voogasalad.data.converter.element;

import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.BasicGameObjectConfigurations;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.utilities.GameElement;
import voogasalad.utilities.gameElements.Attacker;

import java.util.HashMap;
import java.util.Map;

/**
 * This class is used in order to initialize entries into argument maps common amongst all the elements.
 *
 * @author Justin Havas
 */
public class ArgumentMapInitializer {
    private Map<String,Object> myMap;

    public ArgumentMapInitializer(AuthoringFeature authoringFeature) {
        myMap = new HashMap<>();
        myMap.put(GameElement.NAME, authoringFeature.getName());
    }

    /**
     * Initializes the argument map with attributes common amongst all elements with a BasicGameObjectConfigurations.
     * @param bgoc BasicGameObjectConfigurations used to initialize common attributes of the argument map
     */
    public void initializeBasicGameObjectConfigurations(BasicGameObjectConfigurations bgoc) throws UnknownDataException {
        myMap.put(Attacker.HEALTH, bgoc.getHealth());
        myMap.put("size", bgoc.getSize());
        myMap.put("cost", bgoc.getValue());
    }

    /**
     * Returns the argument map being initialized.
     */
    public Map<String,Object> getMap() {
        return myMap;
    }
}
