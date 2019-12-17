package voogasalad.authoring.controller.mapbuilder;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;
import voogasalad.authoring.controller.Viewable;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class MapPane implements Viewable, ElementSelectorObserver, PropertiesObserver {
    private static final int CELL_SIZE = 30;
    private static final String RESOURCE_FILE = "voogasalad.authoring.controller.mapbuilder.resources.MapBuilderResources";
    private static final String ERROR_CHANGE_MAP_TILE = "errorChangeMapTile";

    private GridPane mapGrid;
    private int numRows;
    private int numCols;
    private MapElementType[][] mapDataArray;
    private Map<MapElementType, Image> elementImages;
    private Map<MapElementType, Set<ImageView>> imageViewMap;
    private MapElementType curMapElement;
    private MapElementType defaultMapElement;
    private ResourceBundle displayInfo;

    public MapPane(int defaultRow, int defaultCol, MapElementType defaultMapElement, Image defaultImage) {
        initVars(defaultRow, defaultCol, defaultMapElement, defaultImage);
        initMap();
    }

    public void load(MapElementType[][] mapArray) {
        numRows = mapArray.length;
        numCols = mapArray[0].length;
        initMap();
        elementImages.keySet().forEach(elemType -> {
            imageViewMap.put(elemType, new HashSet<>());
        });
        mapGrid.getChildren().forEach(imageViewNode -> updateNode(imageViewNode, mapArray));
    }

    public MapElementType[][] getMap() {
        return mapDataArray;
    }

    public void setDefaultElementImage(MapElementType element, Image image) {
        elementImages.put(element, image);
        imageViewMap.computeIfAbsent(element, k -> new HashSet<>());
    }

    @Override
    public Node getNode() {
        return mapGrid;
    }

    @Override
    public void updateSelected(MapElementType elementType) {
        curMapElement = elementType;
    }

    @Override
    public void updateImageChange(MapElementType elementType, Image image) {
        elementImages.put(elementType, image);
        imageViewMap.get(elementType).forEach(imageView -> {
            imageView.setImage(image);
        });
    }

    @Override
    public void updateRows(int rows) {
        numRows = rows;
        initMap();
        elementImages.keySet().forEach(elementType -> {
            imageViewMap.put(elementType, new HashSet<>());
        });
    }

    @Override
    public void updateCols(int cols) {
        numCols = cols;
        initMap();
        elementImages.keySet().forEach(elementType -> {
            imageViewMap.put(elementType, new HashSet<>());
        });
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    private void initVars(int defaultRow, int defaultCol, MapElementType defaultMapElement, Image defaultImage) {
        this.displayInfo = ResourceBundle.getBundle(RESOURCE_FILE);
        this.mapDataArray = new MapElementType[defaultRow][defaultCol];
        this.imageViewMap = new EnumMap<>(MapElementType.class);
        this.elementImages = new EnumMap<>(MapElementType.class);
        this.elementImages.put(defaultMapElement, defaultImage);
        this.defaultMapElement = defaultMapElement;

        this.numRows = defaultRow;
        this.numCols = defaultCol;
        this.mapGrid = new GridPane();
        mapGrid.setOnMouseDragged(this::dragOrClick);
        mapGrid.setOnMouseClicked(this::dragOrClick);
    }

    private void initMap() {
        clearData();

        imageViewMap.put(defaultMapElement, new HashSet<>());
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                createCell(elementImages.get(defaultMapElement), row, col);
            }
        }
    }

    private void clearData() {
        mapGrid.getChildren().clear();
        imageViewMap.clear();
        mapDataArray = new MapElementType[numRows][numCols];
    }

    private void createCell(Image image, int row, int col) {
        ImageView cell = new ImageView();
        cell.setFitHeight(CELL_SIZE);
        cell.setFitWidth(CELL_SIZE);
        cell.setImage(image);
        mapGrid.add(cell, col, row);
        mapDataArray[row][col] = defaultMapElement;
        imageViewMap.get(defaultMapElement).add(cell);
    }

    private void dragOrClick(MouseEvent event) {
        Node clickedNode = event.getPickResult().getIntersectedNode();
        if (curMapElement != null && mapGrid.getChildren().contains(clickedNode)) {
            ImageView clickedImg = (ImageView) clickedNode;
            try {
                clickedImg.setImage(elementImages.get(curMapElement));
                imageViewMap.get(mapDataArray[GridPane.getRowIndex(clickedImg)][GridPane.getColumnIndex(clickedImg)]).remove(clickedImg);
                imageViewMap.get(curMapElement).add(clickedImg);
                mapDataArray[GridPane.getRowIndex(clickedImg)][GridPane.getColumnIndex(clickedImg)] = curMapElement;
            } catch (Exception e) {
                // Error Handling Complete
                new Alert(Alert.AlertType.ERROR, displayInfo.getString(ERROR_CHANGE_MAP_TILE)).showAndWait();
            }
        }
    }

    private void updateNode(Node imageViewNode, MapElementType[][] mapArray) {
        ImageView curImageView = (ImageView) imageViewNode;
        int curRow = GridPane.getRowIndex(curImageView);
        int curCol = GridPane.getColumnIndex(curImageView);
        MapElementType curMapElement = mapArray[curRow][curCol];
        curImageView.setImage(elementImages.get(curMapElement));
        imageViewMap.get(mapDataArray[curRow][curCol]).remove(curImageView);
        imageViewMap.get(curMapElement).add(curImageView);
        mapDataArray[curRow][curCol] = curMapElement;
    }
}
