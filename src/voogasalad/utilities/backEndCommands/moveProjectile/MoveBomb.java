package voogasalad.utilities.backEndCommands.moveProjectile;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.gameElements.Projectile;
import voogasalad.utilities.backEndCommands.MoveProjectile;
import voogasalad.utilities.frontEndCommands.effect.ExplodeCommand;
import voogasalad.utilities.gameElements.projectiles.Bomb;

/**
 * This command is responsible for moving the bomb projectile and exploding as necessary
 */
public class MoveBomb extends MoveProjectile {
    public MoveBomb(Projectile projectile) {
        super(projectile);
    }

    @Override
    protected void explode(LevelController level) {
        if(!getProjectile().isExploded()) {
            level.addToFrontEndCommands(new ExplodeCommand(getProjectile().getId(), ((Bomb) getProjectile()).getBlastRadius()));
        }
    }
}
