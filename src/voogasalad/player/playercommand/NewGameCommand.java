package voogasalad.player.playercommand;

import voogasalad.player.IMediator;
import voogasalad.player.PlayerException;

import java.lang.reflect.Method;

public class NewGameCommand implements ICommand {

    private static final String METHOD_NAME = "newGame";

    private IMediator myController;

    public NewGameCommand(IMediator controller) {
        myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME, Boolean.TYPE);
            m.invoke(myController, false);
        } catch(Exception e) {
            throw new PlayerException("Improper Reflection");
        }
    }
}
