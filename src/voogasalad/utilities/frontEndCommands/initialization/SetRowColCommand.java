package voogasalad.utilities.frontEndCommands.initialization;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.player.PlayerException;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.lang.reflect.Method;

/**
 * Called during the initialization stage of the level. Informs frontend the current row number and column number.
 */
public class SetRowColCommand extends FrontEndCommand {
    private static final String METHOD_NAME = "setRowAndCol";
    private static String EXCEPTION_MESSAGE = "Error in EndOfLevelCommand";

    private EngineController myController;
    private int row;
    private int col;

    public SetRowColCommand(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public void setController(EngineController controller) {
        this.myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME, Integer.TYPE, Integer.TYPE);
            m.invoke(myController, row, col);
        } catch (Exception e) {
            throw new PlayerException(e, EXCEPTION_MESSAGE);
        }
    }
}
