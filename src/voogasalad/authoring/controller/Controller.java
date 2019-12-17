package voogasalad.authoring.controller;

import javafx.scene.Node;

public interface Controller {

    void addObserver(Object observer);

    String getName();

    Node getNode();

    void load();
}
