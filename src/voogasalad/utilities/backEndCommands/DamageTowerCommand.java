package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.frontEndCommands.endoflevel.LoseGameCommand;
import voogasalad.utilities.frontEndCommands.status.UpdateHealth;

/**
 * This command damages the tower based on the damage of the attacker
 */
public class DamageTowerCommand implements BackEndCommand {
    private int damage;

    public DamageTowerCommand(int damage){
        this.damage = damage;
    }


    @Override
    public void execute(LevelController level) {
        level.setCurrentHealth(level.getCurrentHealth() - damage);
        if(level.getCurrentHealth() <= 0){
            level.addToFrontEndCommands(new LoseGameCommand());
        }
        else {
            level.addToFrontEndCommands(new UpdateHealth(level.getCurrentHealth()));
        }
    }
}
