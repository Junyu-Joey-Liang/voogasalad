package voogasalad.player;

import javafx.scene.Node;

public interface GUIFactory {

    Node getNode();

    void initialize();

    void createButtons();
}
