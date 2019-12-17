package voogasalad.utilities.backEndCommands.moveProjectile;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.gameElements.Projectile;
import voogasalad.utilities.backEndCommands.MoveProjectile;

/**
 * This is the arrow mvoe command. This is responsible for moving the arrow and hiting the target if intersected
 */
public class MoveArrow extends MoveProjectile {
    public MoveArrow(Projectile projectile) {
        super(projectile);
    }

    @Override
    protected void explode(LevelController level) {
        getProjectile().setExploded(true);
    }
}
