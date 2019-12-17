package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.gameElements.Obstacle;

/**
 * This is the base obstacle command. how it affects the target is in the sub command
 */
public class ObstacleCommand implements BackEndCommand {
    private Obstacle obstacle;

    public ObstacleCommand(Obstacle damageObstacle){
        this.obstacle = damageObstacle;
    }

    @Override
    public void execute(LevelController level) {
        if(level.getCollisions().containsKey(obstacle.getId()) && level.getCollisions().get(obstacle.getId()).size() > 0){
            for(int id : level.getCollisions().get(obstacle.getId())){
                damageTarget(level,id);
            }
        }
    }

    protected void damageTarget(LevelController level, int id){
        return;
    }

    protected Obstacle getObstacle() {
        return obstacle;
    }
}
