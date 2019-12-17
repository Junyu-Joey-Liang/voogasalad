package voogasalad.data.converter.element;

import voogasalad.data.exception.UnknownDataException;
import voogasalad.utilities.Element;

import java.util.Map;
import java.util.ResourceBundle;

/**
 * This class is used as a factory for elements that have multiple implementations in the game engine.
 *
 * @author Justin Havas
 */
public class ElementFactory {
    private static final char WHITESPACE = ' ';
    private static final char UNDERSCORE = '_';
    private static final String COMMA = ",";
    private static final int PACKAGE_NAME = 0;
    private static final int CLASS_NAME = 1;
    private static final String BUNDLE_PATH = "voogasalad.data.properties.ElementConverter";
    private ResourceBundle myBundle = ResourceBundle.getBundle(BUNDLE_PATH);

    /**
     * Returns an element corresponding to the backend implementation of the provided string
     * and instantiates it with the argument map.
     *
     * @param s string used to retrieve the name of its backend implementation from a properties file
     * @param args argument map used to instantiate element object
     */
    public Element makeElement(String s, Map<String,Object> args) throws UnknownDataException {
        try {
            String[] strings = myBundle.getString(s.replace(WHITESPACE,UNDERSCORE)).split(COMMA);
            Class<?> clazz = Class.forName(strings[PACKAGE_NAME] + "." + strings[CLASS_NAME]);
            return (Element) clazz.getDeclaredConstructor(Map.class).newInstance(args);
        } catch (Exception e) {
            throw new UnknownDataException(e);
        }
    }
}
