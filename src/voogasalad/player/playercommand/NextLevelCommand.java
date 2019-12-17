package voogasalad.player.playercommand;

import voogasalad.player.IMediator;
import voogasalad.player.PlayerException;

import java.lang.reflect.Method;

public class NextLevelCommand implements ICommand {

    private static final String METHOD_NAME = "nextLevel";

    private IMediator myController;

    public NextLevelCommand(IMediator controller) {
        myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME);
            m.invoke(myController);
        } catch(Exception e) {
            throw new PlayerException("Improper Reflection");
        }
    }
}
