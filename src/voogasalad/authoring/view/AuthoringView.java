package voogasalad.authoring.view;

import javafx.scene.Node;

public interface AuthoringView {

    void addNode(String nodeName, Node node);

    Node getNode();
}
