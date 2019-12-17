package voogasalad.authoring.controller.levelbuilder;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.authoringfeature.LevelFeature;
import voogasalad.authoring.controller.AttackerObserver;
import voogasalad.authoring.controller.Controller;
import voogasalad.authoring.controller.DefenderObserver;
import voogasalad.authoring.controller.LevelObserver;
import voogasalad.authoring.controller.ObstacleObserver;
import voogasalad.authoring.controller.levelbuilder.levelbuilderelement.LevelBuilderElement;
import voogasalad.authoring.controller.mapbuilder.MapBuilderObserver;
import voogasalad.authoring.model.Model;
import voogasalad.authoring.model.ModelTypeConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;

public class LevelBuilderController implements Controller, LevelBuilderViewObserver, MapBuilderObserver, AttackerObserver, DefenderObserver, ObstacleObserver {
    public static final String RESOURCES_FOLDER = "voogasalad.authoring.controller.levelbuilder.resources.";
    public static final String ELEMENTS_RESOURCES = "LevelBuilderElements";
    public static final String DISPLAYED_RESOURCES = "DisplayInfo";
    public static final String TITLE = "title";
    public static final String LOAD_ERROR = "loadError";
    public static final String INIT_ERROR = "initError";
    public static final String LEVEL_ONE_DELIMITER = ",";
    public static final String LEVEL_TWO_DELIMITER = ";";
    public static final int OBSERVERS_INDEX = 3;

    private ResourceBundle displayedResources;
    private ResourceBundle elementsResources;
    private LevelBuilderView myView;
    private Model myModel;
    private Map<String, LevelBuilderElement> myElements;
    private Map<String, Set<LevelBuilderElement>> typeUpdateElements;
    private Map<Integer, String> elementPriority;
    private Set<LevelObserver> observers;

    private Map<String, Integer> storedLevels;

    public LevelBuilderController(Model model) {
        initPrivateVars(model);
        initElements();
        attachElementObservers();
    }

    @Override
    public void addObserver(Object observer) {
        observers.add((LevelObserver) observer);
    }

    @Override
    public String getName() {
        return displayedResources.getString(TITLE);
    }

    @Override
    public Node getNode() {
        return myView.getNode();
    }

    @Override
    public void load() {
        storedLevels.clear();
        updateSpecificType(ModelTypeConstants.MAP);
        updateSpecificType(ModelTypeConstants.ATTACKER);
        updateSpecificType(ModelTypeConstants.DEFENDER);
        updateSpecificType(ModelTypeConstants.OBSTACLE);
        Set<AuthoringFeature> levelFeatures = myModel.getAuthoringFeatures(ModelTypeConstants.LEVEL);
        if (levelFeatures != null) {
            levelFeatures.forEach(levelFeature -> {
                storedLevels.put(levelFeature.getName(), levelFeature.getID());
                myView.addLevel(levelFeature.getName());
                observers.forEach(LevelObserver::updateLevels);
            });
        }
    }

    @Override
    public void storeLevel() {
        try {
            LevelFeature newLevel = new LevelFeature();
            myElements.forEach((elementBuilderName, elementBuilder) -> newLevel.setVal(elementBuilderName, elementBuilder.getVal()));
            if (storedLevels.get(newLevel.getName()) != null) {
                myModel.replaceAuthoringFeature(ModelTypeConstants.LEVEL, storedLevels.get(newLevel.getName()), newLevel);
            } else {
                int ID = myModel.addAuthoringFeature(ModelTypeConstants.LEVEL, newLevel);
                storedLevels.put(newLevel.getName(), ID);
            }
            myView.addLevel(newLevel.getName());
            observers.forEach(LevelObserver::updateLevels);
        } catch (LevelConfigurationException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
            // Error Handling Complete
        }
    }

    @Override
    public void loadLevel(String levelName) {
        LevelFeature newLevel = (LevelFeature) myModel.getAuthoringFeature(storedLevels.get(levelName));
        try {
            myElements.forEach((key, value) -> value.setVal(newLevel.getVal(key)));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, displayedResources.getString(LOAD_ERROR)).showAndWait();
            // Error Handling Complete
        }
    }

    @Override
    public void updateAddMap() {
        updateSpecificType(ModelTypeConstants.MAP);
    }

    @Override
    public void updateAttackers() {
        updateSpecificType(ModelTypeConstants.ATTACKER);
    }

    @Override
    public void updateDefenders() {
        updateSpecificType(ModelTypeConstants.DEFENDER);
    }

    @Override
    public void updateObstacles() {
        updateSpecificType(ModelTypeConstants.OBSTACLE);
    }

    private void initElements() {
        elementsResources.getKeys().asIterator().forEachRemaining(this::initElement);
        elementPriority.forEach((priority, elementName) -> addElementToView(elementName));
    }

    private void initElement(String elementName) {
        String[] allElementInfo = elementsResources.getString(elementName).split(LEVEL_ONE_DELIMITER);
        int priority = Integer.parseInt(allElementInfo[0]);
        String className = allElementInfo[1];
        String[] featureTypes = allElementInfo[2].split(LEVEL_TWO_DELIMITER);

        try {
            LevelBuilderElement newElement = (LevelBuilderElement) Class.forName(className).getDeclaredConstructor().newInstance();
            elementPriority.put(priority, elementName);
            myElements.put(elementName, newElement);
            Arrays.stream(featureTypes).forEach(featureType -> initTypeUpdateElements(featureType, newElement));
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, displayedResources.getString(INIT_ERROR)).showAndWait();
            // Error Handling Complete
        }
    }

    private void initTypeUpdateElements(String featureType, LevelBuilderElement newElement) {
        if (featureType.isBlank()) {
            return;
        }
        if (!typeUpdateElements.containsKey(featureType)) {
            typeUpdateElements.put(featureType, new HashSet<>());
        }
        typeUpdateElements.get(featureType).add(newElement);
    }

    private void addElementToView(String elementName) {
        myView.addElement(myElements.get(elementName).getNode(), myElements.get(elementName).getName());
    }

    private void attachElementObservers() {
        myElements.keySet().forEach(element -> {
            String allObservers = elementsResources.getString(element).split(LEVEL_ONE_DELIMITER)[OBSERVERS_INDEX];
            if (!allObservers.isBlank()) {
                String[] observerList = allObservers.split(LEVEL_TWO_DELIMITER);
                for (String observer : observerList) {
                    if (myElements.containsKey(observer)) {
                        myElements.get(element).addObservers(myElements.get(observer));
                    }
                }
            }
        });
    }

    private void updateSpecificType(String type) {
        typeUpdateElements.get(type).forEach(levelBuilderElement -> {
            if (myModel.getAuthoringFeatures(type) != null) {
                levelBuilderElement.updateElement(myModel.getAuthoringFeatures(type));
            }
        });
    }

    private void initPrivateVars(Model model) {
        this.displayedResources = ResourceBundle.getBundle(RESOURCES_FOLDER + DISPLAYED_RESOURCES);
        this.elementsResources = ResourceBundle.getBundle(RESOURCES_FOLDER + ELEMENTS_RESOURCES);
        this.myView = new LevelBuilderView(this);
        this.myModel = model;
        this.myElements = new HashMap<>();
        this.typeUpdateElements = new HashMap<>();
        this.storedLevels = new HashMap<>();
        this.observers = new HashSet<>();
        this.elementPriority = new TreeMap<>();
    }
}
