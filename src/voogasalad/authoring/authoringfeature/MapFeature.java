package voogasalad.authoring.authoringfeature;

import javafx.util.Pair;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;

import java.util.Map;

public class MapFeature implements AuthoringFeature {
    private int ID;
    private String name;
    private int numRows;
    private int numCols;
    private Map<MapElementType, String> elementImagePaths;
    private Pair<MapElementType, String> defaultElementInfo;
    private MapElementType[][] mapData;

    public MapFeature(String name, int numRows, int numCols, Map<MapElementType, String> elementImagePaths, Pair<MapElementType, String> defaultElementInfo, MapElementType[][] mapData) {
        this.name = name;
        this.numRows = numRows;
        this.numCols = numCols;
        this.elementImagePaths = elementImagePaths;
        this.defaultElementInfo = defaultElementInfo;
        MapElementType[][] mapDataCopy = new MapElementType[mapData.length][];
        for (int row = 0; row < mapData.length; row++) {
            mapDataCopy[row] = mapData[row].clone();
        }
        this.mapData = mapDataCopy;
    }

    @Override
    public void addID(int ID) {
        this.ID = ID;
    }

    @Override
    public int getID() {
        return this.ID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public Map<MapElementType, String> getElementImagePaths() {
        return elementImagePaths;
    }

    public MapElementType[][] getMapData() {
        MapElementType[][] mapDataCopy = new MapElementType[mapData.length][];
        for (int row = 0; row < mapData.length; row++) {
            mapDataCopy[row] = mapData[row].clone();
        }
        return mapDataCopy;
    }

    public Pair<MapElementType, String> getDefaultElementInfo() {
        return defaultElementInfo;
    }

}
