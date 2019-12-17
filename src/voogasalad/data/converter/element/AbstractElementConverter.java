package voogasalad.data.converter.element;

import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.LevelFeature;
import voogasalad.data.converter.game.Levels;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.utilities.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This abstract class is used to implement the common iteration used to create a list of elements from the converter
 * classes.
 *
 * @author Justin Havas
 */
public abstract class AbstractElementConverter implements ElementConverter {
    protected Levels levels;

    public AbstractElementConverter(Levels levels) {
        this.levels = levels;
    }

    /**
     * @see ElementConverter#convert(LevelFeature)
     */
    @Override
    public List<Element> convert(LevelFeature levelFeature) {
        List<Element> elementList = new ArrayList<>();
        Map<Integer,Boolean> elementMap = levelFeature.getAvailabilities(getAvailabilityString());
        for (Integer integer : elementMap.keySet()) {
            if (elementMap.get(integer)) {
                elementList.add(convertElement(levels.getAuthoringFeatureWithId(integer)));
            }
        }
        return elementList;
    }

    /**
     * Returns a string corresponding to the type of availabilities map tht will be used in order to
     * determine if an authoring feature should be converted to the element list or not.
     */
    public abstract String getAvailabilityString();

    /**
     * Returns a single Element converted from the provided authoring feature.
     * @param authoringFeature authoringFeature used to create level element
     */
    public abstract Element convertElement(AuthoringFeature authoringFeature) throws UnknownDataException;
}
