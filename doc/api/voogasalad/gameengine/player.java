package voogasalad.gameengine;

import java.awt.*;

public interface player {

    void addElement(GameObject item, Point location);
    void moveElement(GameObject item);
}
