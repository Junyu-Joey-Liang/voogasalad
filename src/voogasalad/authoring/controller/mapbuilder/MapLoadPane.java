package voogasalad.authoring.controller.mapbuilder;

import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import voogasalad.authoring.controller.Viewable;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MapLoadPane implements Viewable, StoreObserver {
    private static final String ID_STORED_MAPS = "MapStoredMaps";

    private HBox mainNode;
    private ComboBox<String> comboBox;
    private Set<LoadPaneObserver> observers;

    public MapLoadPane() {
        mainNode = new HBox();
        observers = new HashSet<>();
        comboBox = new ComboBox<>();
        comboBox.setId(ID_STORED_MAPS);
        comboBox.valueProperty().addListener((obs, old, neww) -> updateObservers(neww));
        mainNode.getChildren().add(comboBox);
    }

    public void addMapFeature(String name) {
        if (comboBox.getItems().contains(name)) {
            return;
        }
        comboBox.getItems().add(name);
        comboBox.getSelectionModel().select(name);
    }

    public void updateObservers(String mapName) {
        observers.forEach(observer -> observer.updateLoad(mapName));
    }

    public void addObservers(LoadPaneObserver... observers) {
        this.observers.addAll(Arrays.asList(observers));
    }

    @Override
    public Node getNode() {
        return mainNode;
    }

    @Override
    public void updateStore(String mapName) {
        addMapFeature(mapName);
    }
}
