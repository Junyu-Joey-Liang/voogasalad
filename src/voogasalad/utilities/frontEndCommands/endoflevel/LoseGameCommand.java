package voogasalad.utilities.frontEndCommands.endoflevel;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.player.PlayerException;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.lang.reflect.Method;

/**
 * Should be called when the engine decides that the player lost the game.
 */
public class LoseGameCommand extends FrontEndCommand {

    private static String METHOD_NAME = "loseGame";
    private static String EXCEPTION_MESSAGE = "Error in LoseGameCommand";

    private EngineController myController;

    @Override
    public void setController(EngineController controller) {
        this.myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME);
            m.invoke(myController);
        } catch (Exception e) {
            throw new PlayerException(e, EXCEPTION_MESSAGE);
        }
    }
}
