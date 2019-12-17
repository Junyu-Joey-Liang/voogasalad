package voogasalad.utilities.frontEndCommands.initialization;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.player.PlayerException;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.lang.reflect.Method;

/**
 * Should be called at the initialization stage of each LEVEL.
 * Informs the front end which defender will be available for that level.
 */
public class AddToDefenderTabCommand extends FrontEndCommand {
    private static final String METHOD_NAME = "addToDefenderTab";
    private static String EXCEPTION_MESSAGE = "Error in EndOfLevelCommand";

    private EngineController myController;
    private String filePath;
    private String defenderType;
    private int cost;

    /**
     * @param filePath = Image file path of the defender
     * @param defenderType = Type of the defender (String)
     * @param cost = cost of the defender
     */
    public AddToDefenderTabCommand(String filePath, String defenderType, int cost) {
        this.filePath = filePath;
        this.defenderType = defenderType;
        this.cost = cost;
    }

    @Override
    public void setController(EngineController controller) {
        this.myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME, String.class, String.class, Integer.TYPE);
            m.invoke(myController, filePath, defenderType, cost);
        } catch (Exception e) {
            throw new PlayerException(e, EXCEPTION_MESSAGE);
        }
    }
}
