package voogasalad.authoring.controller.mapbuilder;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.MapFeature;
import voogasalad.authoring.authoringfeature.enumtypes.MapElementType;
import voogasalad.authoring.controller.Controller;
import voogasalad.authoring.model.Model;
import voogasalad.authoring.model.ModelTypeConstants;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class MapBuilderController implements Controller, StoreObserver, LoadPaneObserver {
    public static final String IMAGE_FOLDER_PATH = "/src/voogasalad/authoring/resources/mapimages/";
    public static final String RESOURCES_PATH = "voogasalad.authoring.controller.mapbuilder.resources.";
    public static final String IMAGE_PROPERTIES = "ImagePaths";
    public static final String MAP_BUILDER_PROPERTIES = "MapBuilderResources";
    public static final String TITLE_KEY = "title";
    public static final String DEFAULT_NAME_KEY = "defaultname";
    public static final String USER_DIR = "user.dir";
    public static final String ERROR_DEFAULT_IMAGE = "errorImageLoading";
    public static final int STARTING_ROWS = 10;
    public static final int STARTING_COLS = 10;
    public static final MapElementType DEFAULT_ELEMENT = MapElementType.BUILDABLE;

    public static final int MAP_PANE_WIDTH = 800;
    public static final int MAP_PANE_HEIGHT = 800;
    public static final int MAP_ELEM_PANE_WIDTH = 1000;
    public static final int MAP_ELEM_PANE_HEIGHT = 150;

    private ResourceBundle imagePaths;
    private ResourceBundle mapBuilderResources;
    private Map<String, Integer> storedMaps;
    private Set<MapBuilderObserver> observers;
    private Model model;

    private BorderPane mainPane;
    private MapPane mapPane;
    private MapElementsPane mapElemPane;
    private MapPropertiesPane mapPropertiesPane;
    private MapLoadPane mapLoadPane;

    public MapBuilderController(Model model) {
        initDataVars(model);
        initIndividualPanes();
        this.mainPane = new BorderPane();
        setPanes();
    }

    @Override
    public void addObserver(Object observer) {
        observers.add((MapBuilderObserver) observer);
    }

    @Override
    public String getName() {
        return mapBuilderResources.getString(TITLE_KEY);
    }

    @Override
    public Node getNode() {
        return mainPane;
    }

    @Override
    public void load() {
        storedMaps.clear();
        Set<AuthoringFeature> mapFeatures = model.getAuthoringFeatures(ModelTypeConstants.MAP);
        if (mapFeatures != null) {
            mapFeatures.forEach(mapFeature -> {
                storedMaps.put(mapFeature.getName(), mapFeature.getID());
                mapLoadPane.updateStore(mapFeature.getName());
                observers.forEach(MapBuilderObserver::updateAddMap);
            });
        }
    }

    @Override
    public void updateStore(String mapName) {
        MapFeature storedFeature = new MapFeature(mapPropertiesPane.getName(), mapPane.getNumRows(), mapPane.getNumCols(), mapElemPane.getElementImagePairs(), new Pair<>(DEFAULT_ELEMENT, mapElemPane.getElementImage(DEFAULT_ELEMENT)), mapPane.getMap());
        if (storedMaps.get(mapPropertiesPane.getName()) != null) {
            model.replaceAuthoringFeature(ModelTypeConstants.MAP, storedMaps.get(mapPropertiesPane.getName()), storedFeature);
        } else {
            int id = model.addAuthoringFeature(ModelTypeConstants.MAP, storedFeature);
            storedMaps.put(mapPropertiesPane.getName(), id);
        }
        mapLoadPane.updateStore(mapName);
        observers.forEach(MapBuilderObserver::updateAddMap);
    }

    @Override
    public void updateLoad(String mapName) {
        MapFeature mapFeature = (MapFeature) model.getAuthoringFeature(storedMaps.get(mapName));
        mapPropertiesPane.updatePane(mapFeature.getName(), mapFeature.getNumRows(), mapFeature.getNumCols());
        mapFeature.getElementImagePaths().forEach((elementType, imagePath) -> mapElemPane.updateElementImage(elementType, imagePath));
        mapPane.load(mapFeature.getMapData());
    }

    private void initDataVars(Model model) {
        this.observers = new HashSet<>();
        this.storedMaps = new HashMap<>();
        this.model = model;
        this.imagePaths = ResourceBundle.getBundle(RESOURCES_PATH + IMAGE_PROPERTIES);
        this.mapBuilderResources = ResourceBundle.getBundle(RESOURCES_PATH + MAP_BUILDER_PROPERTIES);
    }

    private void initIndividualPanes() {
        mapElemPane = new MapElementsPane();
        imagePaths.getKeys().asIterator().forEachRemaining(mapElement -> mapElemPane.addElement(MapElementType.valueOf(mapElement), IMAGE_FOLDER_PATH + imagePaths.getString(mapElement)));

        createMapPane(mapElemPane.getElementImage(DEFAULT_ELEMENT));
        initMapPane();
        mapPropertiesPane = new MapPropertiesPane(mapBuilderResources.getString(DEFAULT_NAME_KEY) + "_" + storedMaps.size(), STARTING_ROWS, STARTING_COLS);
        mapLoadPane = new MapLoadPane();

        addPaneObservers();
    }

    private void setPanes() {
        mainPane.setCenter(addToScrollPane(mapPane.getNode(), MAP_PANE_WIDTH, MAP_PANE_HEIGHT));
        mainPane.setBottom(addToScrollPane(mapElemPane.getNode(), MAP_ELEM_PANE_WIDTH, MAP_ELEM_PANE_HEIGHT));
        mainPane.setLeft(addToScrollPane(mapPropertiesPane.getNode(), 0, 0));
        mainPane.setTop(addToScrollPane(mapLoadPane.getNode(), 0, 0));
    }

    private void addPaneObservers() {
        mapLoadPane.addObservers(this);
        mapElemPane.addObserver(mapPane);
        mapPropertiesPane.addPropertiesObserver(mapPane);
        mapPropertiesPane.addStoreObservers(this);
    }

    private Node addToScrollPane(Node node, int width, int height) {
        ScrollPane newScrollPane = new ScrollPane(node);
        if (width > 0) {
            newScrollPane.setPrefViewportWidth(width);
        }
        if (height > 0) {
            newScrollPane.setPrefViewportHeight(height);
        }
        newScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        newScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return newScrollPane;
    }

    private void createMapPane(String defaultImagePath) {
        Image defaultImage = null;
        try {
            defaultImage = new Image(new FileInputStream(System.getProperty(USER_DIR) + defaultImagePath));
        } catch (Exception e) {
            // Error Handling Complete
            new Alert(Alert.AlertType.ERROR, mapBuilderResources.getString(ERROR_DEFAULT_IMAGE)).showAndWait();
        }
        mapPane = new MapPane(STARTING_ROWS, STARTING_COLS, DEFAULT_ELEMENT, defaultImage);
    }

    private void initMapPane() {
        imagePaths.getKeys().asIterator().forEachRemaining(mapElementType -> {
            try {
                mapPane.setDefaultElementImage(MapElementType.valueOf(mapElementType), new Image(new FileInputStream(System.getProperty(USER_DIR) + IMAGE_FOLDER_PATH + imagePaths.getString(mapElementType))));
            } catch (Exception e) {
                //Error Handling Complete
                new Alert(Alert.AlertType.ERROR, mapBuilderResources.getString(ERROR_DEFAULT_IMAGE)).showAndWait();
            }
        });
    }
}
