package voogasalad.data.converter.element;

import voogasalad.authoring.authoringfeature.Attacker;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.LevelFeature;
import voogasalad.data.converter.game.Levels;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.utilities.Element;
import voogasalad.utilities.GameElement;

import java.util.Map;

/**
 * This class is used to convert an attacker authoring feature into a single attacker element.
 *
 * @author Justin Havas
 */
public class Attackers extends AbstractElementConverter implements ElementConverter {

    public Attackers(Levels levels) {
        super(levels);
    }

    /**
     * @see AbstractElementConverter#convertElement(AuthoringFeature)
     */
    @Override
    public Element convertElement(AuthoringFeature authoringFeature) throws UnknownDataException {
        Attacker authoringAttacker = (Attacker) authoringFeature;
        ArgumentMapInitializer initializer = new ArgumentMapInitializer(authoringAttacker);
        initializer.initializeBasicGameObjectConfigurations(authoringAttacker.getBasicConfigurations());
        Map<String,Object> attackerArgs = initializer.getMap();
        attackerArgs.put(GameElement.IMAGE, authoringAttacker.getImagePath());
        attackerArgs.put(voogasalad.utilities.gameElements.Attacker.DAMAGE, authoringAttacker.getDamage());
        attackerArgs.put(voogasalad.utilities.gameElements.Attacker.SPEED, authoringAttacker.getSpeed());
        return new voogasalad.utilities.gameElements.Attacker(attackerArgs);
    }

    /**
     * @see AbstractElementConverter#getAvailabilityString()
     */
    @Override
    public String getAvailabilityString() {
        return LevelFeature.ATTACKER;
    }
}
