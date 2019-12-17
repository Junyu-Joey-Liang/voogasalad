package voogasalad.utilities.frontEndCommands.effect;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.player.PlayerException;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.lang.reflect.Method;

public class ExplodeCommand extends FrontEndCommand {
    private static final String METHOD_NAME = "showEffect";

    private int ID;
    private double radius;
    private EngineController controller;

    public ExplodeCommand(int id, double radius) {
        this.ID = id;
        this.radius = radius;
    }

    @Override
    public void setController(EngineController controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        System.out.println(ID + " explodes!!!!" + radius);
        try {
            Method m = controller.getClass().getDeclaredMethod(METHOD_NAME, Integer.TYPE, Double.TYPE, String.class);
            m.invoke(controller, ID, radius, "Explosion");
        } catch (Exception e) {
            throw new PlayerException(e, "ExplodeCommand is not working well");
        }
    }
}
