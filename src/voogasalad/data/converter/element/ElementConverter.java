package voogasalad.data.converter.element;

import voogasalad.authoring.authoringfeature.LevelFeature;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.utilities.Element;

import java.util.List;

/**
 * This interface is represents the converter for each type of element needing to be added to a backend level.
 *
 * @author Justin Havas
 */
public interface ElementConverter {

    /**
     * Convert the elements available using the provided level feature.
     * @param levelFeature levelFeature to use in order to get element availabilities
     */
    public List<Element> convert(LevelFeature levelFeature) throws UnknownDataException;
}
