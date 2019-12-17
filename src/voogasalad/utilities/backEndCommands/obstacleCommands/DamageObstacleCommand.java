package voogasalad.utilities.backEndCommands.obstacleCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.backEndCommands.DamageElementCommand;
import voogasalad.utilities.backEndCommands.ObstacleCommand;
import voogasalad.utilities.gameElements.Attacker;
import voogasalad.utilities.gameElements.Obstacle;
import voogasalad.utilities.gameElements.obstacles.DamageObstacle;

/**
 * This is the obstacle command which gives damage to any attacker that goes over it
 */
public class DamageObstacleCommand extends ObstacleCommand implements BackEndCommand {

    public DamageObstacleCommand(Obstacle damageObstacle) {
        super(damageObstacle);
    }

    @Override
    protected void damageTarget(LevelController level, int id) {
        if(!((DamageObstacle)getObstacle()).getAttackers().contains(id)) {
            DamageElementCommand command = new DamageElementCommand((Attacker) level.getActiveElements().get(id), ((DamageObstacle) getObstacle()).getDamage());
            command.execute(level);
        }
        ((DamageObstacle)getObstacle()).getAttackers().add(id);
    }
}
