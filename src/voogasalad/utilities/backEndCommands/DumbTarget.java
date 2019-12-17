package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.gameElements.Defender;

/**
 * This is the command which the defender who has a dumb targeting system. This means when something is in it's radius it will shoot
 * a specific heading regardless of where the object is
 */
public class DumbTarget implements BackEndCommand {
    public static final int FULL_RADIUS = 360;
    public static final String HEADING = "heading";
    private Defender defender;

    public DumbTarget(Defender defender){
        this.defender = defender;
    }

    @Override
    public void execute(LevelController level) {
        if(level.getCollisions().containsKey(defender.getId()) && level.getCollisions().get(defender.getId()).size() > 0) {
            defender.setCounter(0);
            int offSet = FULL_RADIUS / defender.getNumProjectiles();
            for (int i = 0; i < defender.getNumProjectiles(); i++) {
                int heading = i * offSet + defender.getHeading();
                level.getLevelElement(defender.getProjectile().getName()).getInitialParams().put(HEADING, heading);
                level.newActiveElement(defender.getProjectile().getName(), defender.getLocation());
            }
        }
    }
}
