package voogasalad.utilities.frontEndCommands.initialization;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.player.PlayerException;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.lang.reflect.Method;

/**
 * Should be called at the initialization stage of each level. The frontend will automatically
 * place the map tile to the given row and col.
 *
 * Important: This should be called after SetRowColCommand has been added to the list of frontendCommands!!!!!
 */
public class AddMapTileCommand extends FrontEndCommand {
    private static final String METHOD_NAME = "addMapTile";
    private static String EXCEPTION_MESSAGE = "Error in AddMapTileCommand";

    private EngineController myController;
    private int row;
    private int col;
    private String filePath;

    /**
     * @param filePath = File path of the image for the map tile
     * @param row
     * @param col
     */
    public AddMapTileCommand(String filePath, int row, int col) {
        this.row = row;
        this.col = col;
        this.filePath = filePath;
    }

    @Override
    public void setController(EngineController controller) {
        this.myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME, String.class, Integer.TYPE, Integer.TYPE);
            m.invoke(myController, filePath, row, col);
        } catch (Exception e) {
            throw new PlayerException(e, EXCEPTION_MESSAGE);
        }
    }
}
