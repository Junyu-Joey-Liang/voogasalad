package voogasalad.player.playercommand;

import voogasalad.player.IMediator;
import voogasalad.player.PlayerException;
import voogasalad.player.PlayerMediator;

import java.lang.reflect.Method;

public class PlayGameCommand implements ICommand {
    private static final String METHOD_NAME = "playGame";

    private IMediator myController;

    public PlayGameCommand(IMediator controller) {
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
