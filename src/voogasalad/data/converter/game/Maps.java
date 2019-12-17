package voogasalad.data.converter.game;

import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.MapFeature;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.gameengine.game.Game;
import voogasalad.gameengine.game.Level;

import java.util.Map;
import java.util.Set;

/**
 * This class is used to add a map for a level within a Game object to use.
 *
 * @author Justin Havas
 */
public class Maps {

    /**
     * Sets the map attributes for a particular level utilizing a MapFeature.
     *
     * @param authoringFeature authoringFeature implemented by a MapFeature
     * @param level level that needs its map to be set
     */
    public void convert(AuthoringFeature authoringFeature, Level level) throws UnknownDataException {
        MapFeature mapFeature = (MapFeature) authoringFeature;
        level.setLevelMap(mapFeature.getMapData(), mapFeature.getElementImagePaths());
    }
}
