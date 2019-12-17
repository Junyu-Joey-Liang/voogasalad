package voogasalad.utilities.frontEndCommands.backendcommandresponse;

import javafx.scene.paint.Color;
import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;
import java.lang.reflect.Method;

/**
 * This frontEndCommand should be called when the backend decides that the defender cannot be
 * placed in the given location. It tells the frontend to draw a red circle.
 */
public class UnableToPlaceCommand extends FrontEndCommand {
    private static final String METHOD_NAME = "drawCircle";

    private double radius;
    private EngineController myController;

    /**
     * @param radius = put the radius of the circle to be drawn.
     */
    public UnableToPlaceCommand(double radius) {
        this.radius = radius;
    }

    @Override
    public void setController(EngineController controller) {
        myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME, Color.class, Double.TYPE);
            m.invoke(myController, Color.RED, radius);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
