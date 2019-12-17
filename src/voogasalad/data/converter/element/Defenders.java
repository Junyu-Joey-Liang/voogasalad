package voogasalad.data.converter.element;

import voogasalad.authoring.authoringfeature.*;
import voogasalad.data.converter.game.Levels;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.utilities.Element;
import voogasalad.utilities.GameElement;

import java.util.Map;

/**
 * This class is used to convert an defender authoring feature into a single defender element.
 *
 * @author Justin Havas
 */
public class Defenders extends AbstractElementConverter implements ElementConverter {

    public Defenders(Levels levels) {
        super(levels);
    }

    /**
     * @see AbstractElementConverter#convertElement(AuthoringFeature)
     */
    @Override
    public Element convertElement(AuthoringFeature authoringFeature) throws UnknownDataException {
        Defender authoringDefender = (Defender) authoringFeature;
        ArgumentMapInitializer initializer = new ArgumentMapInitializer(authoringDefender);
        initializer.initializeBasicGameObjectConfigurations(authoringDefender.getBasicConfigurations());
        Map<String,Object> defenderArgs = initializer.getMap();
        defenderArgs.put(GameElement.IMAGE, authoringDefender.getImagePath());
        defenderArgs.put(GameElement.RADIUS, authoringDefender.getRadiusOfVision());
        defenderArgs.put(voogasalad.utilities.gameElements.Defender.NUM_PROJECTILE, authoringDefender.getNumProjectile());
        defenderArgs.put(voogasalad.utilities.gameElements.Defender.TARGET_TYPE, authoringDefender.getTargetingMechanism());
        defenderArgs.put(voogasalad.utilities.gameElements.Defender.COOL_DOWN, authoringDefender.getCoolDown());
        defenderArgs.put(voogasalad.utilities.gameElements.Defender.PROJECTILE, new Projectiles(levels).convertElement(authoringDefender));
        return new voogasalad.utilities.gameElements.Defender(defenderArgs);
    }

    /**
     * @see AbstractElementConverter#getAvailabilityString()
     */
    @Override
    public String getAvailabilityString() {
        return LevelFeature.DEFENDER;
    }
}
