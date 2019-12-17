package voogasalad.player.playercommand;

import voogasalad.player.IMediator;
import voogasalad.player.PlayerException;

import java.lang.reflect.Method;

public class StopGameCommand implements ICommand {
    private static final String METHOD_NAME = "stopGame";

    private IMediator myController;

    public StopGameCommand(IMediator controller) {
        myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME);
            m.invoke(myController);
        } catch(Exception e) {
            System.out.println(e.getMessage());
//            throw new PlayerException("Improper Reflection");
        }
    }
}
