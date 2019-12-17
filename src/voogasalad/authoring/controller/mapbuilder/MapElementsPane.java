package voogasalad.authoring.controller.mapbuilder;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;
import voogasalad.authoring.controller.Viewable;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MapElementsPane implements Viewable, MapElementObserver {
    private HBox elementsPane;
    private Set<MapElement> mapElements;
    private Set<ElementSelectorObserver> observers;

    public MapElementsPane() {
        this.observers = new HashSet<>();
        this.elementsPane = new HBox();
        this.mapElements = new TreeSet<>();
        elementsPane.setOnMouseClicked(this::clickHandler);
    }

    public void addObserver(ElementSelectorObserver observer) {
        observers.add(observer);
    }

    public void addElement(MapElementType elementType, String imagePath) {
        MapElement newMapElement = new MapElement(elementType, imagePath);
        newMapElement.addObserver(this);
        mapElements.add(newMapElement);
        elementsPane.getChildren().clear();
        mapElements.forEach(mapElement -> {
            elementsPane.getChildren().add(mapElement.getNode());
        });
    }

    public Map<MapElementType, String> getElementImagePairs() {
        Map newMap = new EnumMap<>(MapElementType.class);
        mapElements.forEach(mapElement -> {
            newMap.put(mapElement.getProperties().getKey(), mapElement.getProperties().getValue());
        });
        return newMap;
    }

    @Override
    public Node getNode() {
        return elementsPane;
    }

    @Override
    public void updateImageChange(MapElementType elementType, Image image) {
        observers.forEach(observer -> observer.updateImageChange(elementType, image));
    }

    public String getElementImage(MapElementType elementType) {
        for (MapElement mapElement : mapElements) {
            if (mapElement.getProperties().getKey().equals(elementType)) {
                return mapElement.getProperties().getValue();
            }
        }
        return null;
    }

    public void updateElementImage(MapElementType elementType, String imagePath) {
        mapElements.forEach(mapElement -> {
            if (mapElement.getProperties().getKey().equals(elementType)) {
                mapElement.changeImage(imagePath);
            }
        });
    }

    private void clickHandler(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (elementsPane.getChildren().contains(clickedNode) || elementsPane.getChildren().contains(clickedNode.getParent())) {
            mapElements.forEach(mapElement -> {
                if (mapElement.getNode().equals(clickedNode) || mapElement.getNode().equals(clickedNode.getParent())) {
                    mapElement.selected();
                    observers.forEach(observer -> observer.updateSelected(mapElement.getProperties().getKey()));
                } else {
                    mapElement.unselected();
                }
            });
        }
    }
}
