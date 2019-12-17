package voogasalad.utilities.backEndCommands;

import voogasalad.gameengine.game.LevelController;
import voogasalad.utilities.BackEndCommand;
import voogasalad.utilities.frontEndCommands.updateelements.RemoveCommand;

/**
 * this removes an active element from the screen
 */
public class RemoveActiveElement implements BackEndCommand {
    private int id;
    public RemoveActiveElement(int id){
        this.id = id;
    }


    @Override
    public void execute(LevelController level) {
        level.removeActiveElement(id);
        level.addToFrontEndCommands(new RemoveCommand(id));
    }
}
