package voogasalad.gameengine;

import voogasalad.utilities.Command;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface engine {
    void createElement(String elementName, Map<String,String> params);
    void addElementToScreen(String elementType, Point location);
    List<Command> updateGame();
    boolean canPlace(String elementType, Point location);
}
