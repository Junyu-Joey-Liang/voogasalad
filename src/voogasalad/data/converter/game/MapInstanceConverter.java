package voogasalad.data.converter.game;

import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.LevelFeature;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is used to convert maps with IDs as keys to maps with names as keys.
 *
 * @author Justin Havas
 */
public class MapInstanceConverter {
    private static final List<String> costs = List.of(
            LevelFeature.DEFENDER_COSTS,
            LevelFeature.OBSTACLE_COSTS
    );

    private Map<Integer, AuthoringFeature> idMap;

    public MapInstanceConverter(Map<Integer, AuthoringFeature> idMap) {
        this.idMap = idMap;
    }

    /**
     * Returns a converted map that replaces the key IDs with names when the data structure mapped to is an array of integers.
     *
     * @param map map to have its key values converted
     */
    public Map<String,Integer[]> convertAttackerOrderMapFromIdsToNames(Map<Integer,Integer[]> map) {
        Map<String,Integer[]> myMap = new HashMap<>();
        for (Integer integer : map.keySet()) {
            myMap.put(idMap.get(integer).getName(), map.get(integer));
        }
        return myMap;
    }

    /**
     * Returns a converted map that replaces the key IDs with names when the data structure mapped to is an integer.
     *
     * @param map map to have its key values converted
     */
    public Map<String,Integer> convertMapFromIdsToNames(Map<Integer,Integer> map) {
        Map<String,Integer> myMap = new HashMap<>();
        for (Integer integer : map.keySet()) {
            myMap.put(idMap.get(integer).getName(), map.get(integer));
        }
        return myMap;
    }

    /**
     * Returns a converted map that combines all the cost maps in a level into one map that
     * replaces the key IDs with names when the data structure mapped to is an integer.
     *
     * @param levelFeature levelFeature containing all the cost maps
     */
    public Map<String,Integer> convertCostMapsFromIdsToNames(LevelFeature levelFeature) {
        Map<String,Integer> myMap = new HashMap<>();
        for (String s : costs) {
            Map<Integer,Integer> map = levelFeature.getFeatureValue(s);
            for (Integer integer : map.keySet()) {
                myMap.put(idMap.get(integer).getName(), map.get(integer));
            }
        }
        return myMap;
    }
}
