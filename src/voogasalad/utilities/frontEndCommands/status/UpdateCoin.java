package voogasalad.utilities.frontEndCommands.status;

import voogasalad.gameengine.enginecontroller.EngineController;
import voogasalad.utilities.frontEndCommands.FrontEndCommand;

import java.lang.reflect.Method;

public class UpdateCoin extends FrontEndCommand {
    private static final String METHOD_NAME = "setStatus";
    private static final String COIN = "Coin";

    private int coin;
    private EngineController myController;

    public UpdateCoin(int coin) {
        this.coin = coin;
    }

    @Override
    public void setController(EngineController controller) {
        myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME, String.class, Integer.TYPE);
            m.invoke(myController, COIN, coin);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
