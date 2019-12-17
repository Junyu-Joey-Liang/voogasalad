package voogasalad.data.converter.element;

import voogasalad.data.converter.game.Levels;
import voogasalad.data.exception.UnknownDataException;

/**
 * This class is used as a factory to instantiate an ElementConverter with a concrete implementation.
 *
 * @author Justin Havas
 */
public class ElementConverterFactory {

    /**
     * Returns an element converter corresponding to the type of element being converted.
     *
     * @param s string specifying the name of the element converter class instantiated through reflection
     * @param levels level converter used to construct the abstract element converter
     */
    public ElementConverter makeConverter(String s, Levels levels) throws UnknownDataException {
        try {
            Class<?> clazz = Class.forName(ElementConverter.class.getPackageName() + "." + s);
            return (ElementConverter) clazz.getDeclaredConstructor(Levels.class).newInstance(levels);
        } catch (Exception e) {
            throw new UnknownDataException(e);
        }
    }
}
