package voogasalad.player;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.imageio.ImageIO;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PlayerButton implements IButton {
    private static final String DEFAULT_RESOURCE_PACKAGE = PlayerButton.class.getPackageName() + ".resources.";
    private static final String COMMAND_PATH = "voogasalad.player.playercommand.";

    private static final int BUTTON_DEFAULT_X = 400;
    private static final int BUTTON_GAPS = 50;

    private IMediator mediator;
    private List<Button> buttons;

    public PlayerButton(IMediator mediator) {
        this.mediator = mediator;
        buttons = new ArrayList<>();

    }

    @Override
    public Button render(String key, String label) {
        final String IMAGEFILE_SUFFIXES = String.format(".*\\.(%s)", String.join("|", ImageIO.getReaderFileSuffixes()));

        Button result = new Button();
        if(label.matches(IMAGEFILE_SUFFIXES)) {
            result.setGraphic(new ImageView(new Image(getClass().getResourceAsStream(packageToPathName(DEFAULT_RESOURCE_PACKAGE) + label))));
        } else {
            result.setText(label);
            buttons.add(result);
            result.setLayoutX(BUTTON_DEFAULT_X);
            result.setLayoutY(BUTTON_DEFAULT_X + buttons.size()* BUTTON_GAPS);
        }

        result.setOnAction(event -> onClick(key));
        return result;
    }

    @Override
    public void onClick(String key) {
        try {
            Class<?> clazz = Class.forName(COMMAND_PATH + key);
            Constructor<?> cons = clazz.getConstructor(IMediator.class);
            Object o = cons.newInstance(mediator);
            Method m = o.getClass().getDeclaredMethod("execute");
            m.invoke(o);
        } catch (Exception e) {
            throw new PlayerException("Improper Reflection", e);
        }
    }

    private String packageToPathName(String packageName) {
        return "/" + packageName.replaceAll("\\.", "/");
    }

}
