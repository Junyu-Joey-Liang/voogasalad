package voogasalad.utilities.frontEndCommands.updateelements;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.lang.reflect.Method;

/**
 * Should be called when an element has been removed.
 */
public class RemoveCommand extends FrontEndCommand {

    private static String methodName = "removeNode";

    private EngineController myController;
    private int ID;

    public RemoveCommand(int ID) {
        this.ID = ID;
    }

    @Override
    public void setController(EngineController controller) {
        this.myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(methodName, Integer.TYPE);
            m.invoke(myController, ID);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
