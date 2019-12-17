package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.gameElements.Attacker;

/**
 * This is the damage element command. This is responsible for giving damage to attackers and destroying an attacker if it runs out of health
 */
public class DamageElementCommand implements BackEndCommand {
    private int damage;
    private Attacker element;
    public DamageElementCommand(Attacker element, int damage){
        this.element = element;
        this.damage = damage;
    }
    @Override
    public void execute(LevelController level) {
        if(element == null) {
            return;
        }
        element.setHealth(element.getHealth() - damage);
        if(element.getHealth() <= 0){
            level.setCurrentMoney(level.getCurrentMoney() + level.getAttackerIncome(element.getName()));
            RemoveActiveElement remove = new RemoveActiveElement(element.getId());
            level.setStepScore(level.getAttackerScore(element.getName()));
            remove.execute(level);
        }
    }
}
