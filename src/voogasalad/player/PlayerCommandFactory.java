package voogasalad.player;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class PlayerCommandFactory {

    private static final String COMMAND_PATH = "voogasalad.player.playercommand.";
    private static final String EXECUTE_REFLECTION = "execute";


    private IMediator mediator;

    public PlayerCommandFactory(IMediator mediator) {
        this.mediator = mediator;
    }

    public void execute(String className) {
        try {
            Class<?> clazz = Class.forName(COMMAND_PATH + className);
            Constructor<?> cons = clazz.getConstructor(IMediator.class);
            Object o = cons.newInstance(mediator);
            Method m = o.getClass().getDeclaredMethod(EXECUTE_REFLECTION);
            m.invoke(o);
        } catch (Exception e) {
            throw new PlayerException("Improper Reflection", e);
        }
    }
}
