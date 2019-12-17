package voogasalad.utilities.backEndCommands.obstacleCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.backEndCommands.DamageElementCommand;
import voogasalad.utilities.backEndCommands.ObstacleCommand;
import voogasalad.utilities.backEndCommands.RemoveActiveElement;
import voogasalad.utilities.gameElements.Attacker;
import voogasalad.utilities.gameElements.Obstacle;
import voogasalad.utilities.gameElements.obstacles.HealthObstacle;

/**
 * This is the health obstacle command. this will block all attackers until it runs out of health
 */
public class HealthObstacleCommand extends ObstacleCommand implements BackEndCommand {

    public HealthObstacleCommand(Obstacle damageObstacle) {
        super(damageObstacle);
    }

    @Override
    protected void damageTarget(LevelController level, int id) {
        Attacker attacker = (Attacker)level.getActiveElements().get(id);
        if(attacker == null){
            return;
        }
        int currentHealth = ((HealthObstacle)getObstacle()).getHealth();
        int damage = 0;
        if(attacker.getHealth() < currentHealth) {
            damage = attacker.getHealth();
            ((HealthObstacle) getObstacle()).setHealth(currentHealth - attacker.getHealth());
        }
        else{
            damage = currentHealth;
            BackEndCommand remove = new RemoveActiveElement(getObstacle().getId());
            remove.execute(level);
        }
        BackEndCommand command = new DamageElementCommand(attacker,damage);
        command.execute(level);
    }
}
