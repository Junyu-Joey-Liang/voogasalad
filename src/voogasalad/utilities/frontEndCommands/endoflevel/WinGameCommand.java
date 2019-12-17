package voogasalad.utilities.frontEndCommands.endoflevel;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.player.PlayerException;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.lang.reflect.Method;

/**
 * Should be called when the engine decides that the player wins the game.
 */
public class WinGameCommand extends FrontEndCommand {
    private static String METHOD_NAME = "winGame";
    private static String EXCEPTION_MESSAGE = "Error in WinGameCommand";

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
