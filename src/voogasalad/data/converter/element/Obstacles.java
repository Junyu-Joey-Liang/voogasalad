package voogasalad.data.converter.element;

import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.LevelFeature;
import voogasalad.authoring.authoringfeature.Obstacle;
import voogasalad.data.converter.game.Levels;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.utilities.Element;
import voogasalad.utilities.GameElement;
import voogasalad.utilities.gameElements.obstacles.DamageObstacle;

import java.util.Map;

/**
 * This class is used to convert an obstacle authoring feature into a single obstacle element.
 *
 * @author Justin Havas
 */
public class Obstacles extends AbstractElementConverter implements ElementConverter {

    public Obstacles(Levels levels) {
        super(levels);
    }

    /**
     * @see AbstractElementConverter#convertElement(AuthoringFeature)
     */
    @Override
    public Element convertElement(AuthoringFeature authoringFeature) throws UnknownDataException {
        Obstacle authoringObstacle = (Obstacle) authoringFeature;
        ArgumentMapInitializer initializer = new ArgumentMapInitializer(authoringObstacle);
        initializer.initializeBasicGameObjectConfigurations(authoringObstacle.getBasicConfigurations());
        Map<String,Object> obstacleArgs = initializer.getMap();
        obstacleArgs.put(GameElement.IMAGE, authoringObstacle.getImagePath());
        obstacleArgs.put(DamageObstacle.DAMAGE, authoringObstacle.getDamage());
        return new ElementFactory().makeElement(authoringObstacle.getObstacleType(), obstacleArgs);
    }

    /**
     * @see AbstractElementConverter#getAvailabilityString()
     */
    @Override
    public String getAvailabilityString() {
        return LevelFeature.OBSTACLE;
    }
}
