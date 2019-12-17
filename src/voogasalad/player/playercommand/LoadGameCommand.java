package voogasalad.player.playercommand;

import voogasalad.player.IMediator;
import voogasalad.player.PlayerException;

import java.lang.reflect.Method;

public class LoadGameCommand implements ICommand {
    private static final String METHOD_NAME = "loadGame";

    private IMediator myController;
    private String filePath;

    public LoadGameCommand(IMediator controller, String filePath) {
        myController = controller;
        this.filePath = filePath;
    }

    @Override
    public void execute() {
        try {
            Method m = myController.getClass().getDeclaredMethod(METHOD_NAME, String.class);
            m.invoke(myController, filePath);
        } catch(Exception e) {
            System.out.println(e.getMessage());
//            throw new PlayerException("Improper Reflection");
        }
    }
}
