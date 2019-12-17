package voogasalad.player;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import java.awt.geom.Point2D;

/**
 * Screen in which the game is being played
 */
public class GamePlayScreen {
    private static final double HEIGHT = 750;
    private static final double WIDTH = 800;
    private static final int MIN_Y = 32;
    private Pane myPane;
    private Point2D.Double location;
    private boolean pressed = false;
    private boolean mouseOnScreen = false;

    public GamePlayScreen() {
        myPane = new AnchorPane();
        myPane.setPrefSize(WIDTH, HEIGHT);
        initialize();
    }

    public Pane getPane() {
        return myPane;
    }

    public boolean isMouseOnScreen() {
        return mouseOnScreen;
    }

    public boolean isPressed() {
        boolean bool = pressed;
        pressed = false;
        return bool;
    }

    public Point2D.Double getMouseLocation() {
        return location;
    }

    private void initialize() {
        myPane.setOnMouseEntered(e -> setLocationValue(e));
        myPane.setOnMouseMoved(e -> setLocationValue(e));
        myPane.setOnMouseClicked(e -> {
            setLocationValue(e);
            pressed = true;
        });
        myPane.setOnMouseExited(e -> mouseOnScreen=false);
    }

    private void setLocationValue(MouseEvent e) {
        mouseOnScreen = true;
        location = new Point2D.Double(e.getSceneX(), e.getSceneY()-MIN_Y);
    }
}
