package voogasalad.player.playercommand;

import voogasalad.player.IMediator;
import voogasalad.player.PlayerException;

import java.lang.reflect.Method;

public class ChangeGameCommand implements ICommand {
    private static final String METHOD_NAME = "changeGameAction";

    private String filePath;
    private IMediator myController;

    public ChangeGameCommand(IMediator controller, String filePath) {
        this.filePath = filePath;
        myController = controller;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME, String.class);
            m.invoke(myController, filePath);
        } catch (Exception e) {
            System.out.println("Error in the Change Game Command");
//            throw new PlayerException("Improper Reflection");
        }
    }
}
