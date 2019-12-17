package voogasalad.gameengine.map;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * used to find specific values in property files
 */
public class FindValues extends ResourceBundle {
    private ResourceBundle bundle;

    /**
     * constructor for this class
     * @param filePath the file path of the property file
     */
    public FindValues(String filePath){
        bundle = ResourceBundle.getBundle(filePath);
    }

    /**
     * gets the value specified by the key
     * @param key that maps to the value
     * @return the value that is mapped from the key
     */
    @Override
    protected Object handleGetObject(String key) {
        return bundle.getObject(key);
    }

    /**
     * gets all of the keys in the property file
     * @return all of the keys in the property file
     */
    @Override
    public Enumeration<String> getKeys() {
        return  bundle.getKeys();
    }

    /**
     * determines if a specific value is in the property file
     * @param value value to be found
     * @return false if property file does not contain the value, true if property file contains the value
     */
    public boolean contains(String value){
        for(String key: bundle.keySet()){
            if(bundle.getString(key).equals(value)){
                return true;
            }
        }

        return false;
    }
}
