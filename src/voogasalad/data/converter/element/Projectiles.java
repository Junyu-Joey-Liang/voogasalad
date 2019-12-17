package voogasalad.data.converter.element;

import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.Defender;
import voogasalad.authoring.authoringfeature.LevelFeature;
import voogasalad.authoring.authoringfeature.Projectile;
import voogasalad.data.converter.game.Levels;
import voogasalad.data.exception.UnknownDataException;
import voogasalad.utilities.Element;
import voogasalad.utilities.GameElement;
import voogasalad.utilities.gameElements.projectiles.Bomb;

import java.util.Map;

/**
 * This class is used to convert an projectile authoring feature into a single projectile element.
 *
 * @author Justin Havas
 */
public class Projectiles extends AbstractElementConverter implements ElementConverter {

    public Projectiles(Levels levels) {
        super(levels);
    }

    /**
     * @see AbstractElementConverter#convertElement(AuthoringFeature)
     */
    @Override
    public Element convertElement(AuthoringFeature authoringFeature) throws UnknownDataException {
        Projectile authoringProjectile = (Projectile) levels.getAuthoringFeatureWithId(((Defender) authoringFeature).getProjectile());
        Map<String,Object> projectileArgs = new ArgumentMapInitializer(authoringProjectile).getMap();
        projectileArgs.put(voogasalad.utilities.gameElements.Projectile.SPEED, new Double(authoringProjectile.getSpeed()).intValue());
        projectileArgs.put(GameElement.RADIUS, new Double(authoringProjectile.getRange()).intValue());
        projectileArgs.put(Bomb.BLAST_RADIUS, authoringProjectile.getBlastRadius());
        projectileArgs.put(GameElement.IMAGE, authoringProjectile.getImagePath());
        projectileArgs.put(voogasalad.utilities.gameElements.Projectile.DAMAGE, authoringProjectile.getImpactLevel());
        return new ElementFactory().makeElement(authoringProjectile.getImpactType(), projectileArgs);
    }

    /**
     * @see AbstractElementConverter#getAvailabilityString()
     */
    @Override
    public String getAvailabilityString() {
        // only get available projectiles from defenders
        return LevelFeature.DEFENDER;
    }
}
