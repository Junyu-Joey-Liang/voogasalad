package voogasalad.utilities.frontEndCommands.status;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.lang.reflect.Method;

public class UpdateScore extends FrontEndCommand {
    private static final String METHOD_NAME = "setStatus";

    private int score;
    private EngineController myController;

    public UpdateScore(int score) {
        this.score = score;
    }

    @Override
    public void setController(EngineController controller) {
        myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME, String.class, Integer.TYPE);
            m.invoke(myController, "Score", score);
        } catch (Exception e) {
            System.out.println("Update score not right");
        }
    }
}
