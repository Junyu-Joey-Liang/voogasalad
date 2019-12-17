package voogasalad.utilities.frontEndCommands.updateelements;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;
import java.awt.geom.Point2D;
import java.lang.reflect.Method;

/**
 * Should be called when an element has been formed. (ex. Defender, Attacker, Projectile)
 */
public class CreateCommand extends FrontEndCommand {

    private static String methodName = "createNode";

    private EngineController myController;
    private int ID;
    private Point2D.Double location;
    private double orientation;
    private String type;
    private String filePath;
    private double radius;
    private double size;

    /**
     * @param ID = ID of the element
     * @param filePath = file path of the image of the element
     * @param type = Defender, Attacker, etc
     * @param location = location of the element
     * @param orientation = orientation.
     */
    public CreateCommand(int ID, String filePath, String type, Point2D.Double location, double orientation, double size, double radius) {
        this.ID = ID;
        this.type = type;
        this.location = location;
        this.orientation = orientation;
        this.filePath = filePath;
        this.size = size;
        this.radius = radius;
    }

    @Override
    public void setController(EngineController controller) {
        myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(methodName, Integer.TYPE, String.class, String.class, Point2D.Double.class, Double.TYPE, Double.TYPE, Double.TYPE);
            m.invoke(myController, ID, filePath, type, location, orientation, size, radius);
        } catch (Exception e) {
            System.out.println(ID + " not being created");
        }
    }
}
