package voogasalad.gameengine.frontend;

import javafx.scene.Node;

public interface IEffectManager {

    Node registerEffect(int ID, double size);

    void setLocation(int ID);

    void update();

    void clear();
}
