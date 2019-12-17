package voogasalad.authoring.controller.levelbuilder.levelbuilderelement.attackerflow;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import voogasalad.authoring.controller.Viewable;
import voogasalad.authoring.util.elementbuilders.ImageWithMultipleIntegerSliderElement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

public class AttackerFlowElementView implements Viewable {
    private static final int DEFAULT_MAX_TIME = 100;
    private static final String TIME_SLIDER = "TIME_SLIDER";
    private static final String NUMBER_SLIDER = "NUMBER_SLIDER";
    private static final String MULTIPLIER_SLIDER = "MULTIPLIER_SLIDER";
    private static final double THRESHOLD = 0.001;
    private static final int NODE_HEIGHT = 155;
    private static final int MIN_VAL = 0;
    private static final int IMAGE_SIZE = 50;
    private static final int MIN_MULTIPLIER = 100;
    private static final int MAX_MULTIPLIER = 200;
    private static final int MAX_NUMBER = 50;
    private static final int SLIDER_PREF_WIDTH = 150;
    private static final String DISPLAY_INFO_FILE = "voogasalad.authoring.controller.levelbuilder.resources.ElementDisplayInfo";
    private static final String TIME_SLIDER_LABEL = "timeSliderLabel";
    private static final String NUMBER_SLIDER_LABEL = "numberSliderLabel";
    private static final String MULTIPLIER_SLIDER_LABEL = "multiplierSliderLabel";

    private ResourceBundle displayInfo;
    private HBox attackersBox;
    private ScrollPane attackersScrollPane;
    private Map<Integer, ImageWithMultipleIntegerSliderElement> attackerElements;
    private int maxTime;
    private Set<AttackerFlowElementViewObserver> observers;

    public AttackerFlowElementView(AttackerFlowElementViewObserver observer) {
        displayInfo = ResourceBundle.getBundle(DISPLAY_INFO_FILE);
        observers = new HashSet<>();
        observers.add(observer);
        attackersBox = new HBox();
        attackersBox.setAlignment(Pos.TOP_CENTER);
        attackersScrollPane = new ScrollPane(attackersBox);
        attackersScrollPane.setPrefViewportHeight(NODE_HEIGHT);
        attackerElements = new HashMap<>();
        maxTime = DEFAULT_MAX_TIME;
    }

    @Override
    public Node getNode() {
        return attackersScrollPane;
    }

    public void updateAttacker(int ID, String name, String imagePath) {
        ImageWithMultipleIntegerSliderElement attackerElement = attackerElements.get(ID);
        attackerElement.setName(name);
        attackerElement.setImage(imagePath);
    }

    public void addAttacker(int ID, String name, String imagePath) {
        ImageWithMultipleIntegerSliderElement newElement = new ImageWithMultipleIntegerSliderElement(name, imagePath, IMAGE_SIZE, SLIDER_PREF_WIDTH);
        newElement.addSlider(TIME_SLIDER, displayInfo.getString(TIME_SLIDER_LABEL), MIN_VAL, maxTime, MIN_VAL, ((observable, oldValue, newValue) -> updateTimeChange(ID, (Number) newValue)));
        newElement.addSlider(NUMBER_SLIDER, displayInfo.getString(NUMBER_SLIDER_LABEL), MIN_VAL, MAX_NUMBER, MIN_VAL, ((observable, oldValue, newValue) -> updateNumberChange(ID, (Number) newValue)));
        newElement.addSlider(MULTIPLIER_SLIDER, displayInfo.getString(MULTIPLIER_SLIDER_LABEL), MIN_MULTIPLIER, MAX_MULTIPLIER, MIN_MULTIPLIER, null);
        attackersBox.getChildren().add(newElement.getNode());
        attackerElements.put(ID, newElement);
    }

    public void showMultiplier(int id) {
        attackerElements.get(id).showSlider(MULTIPLIER_SLIDER);
    }

    public void hideMultiplier(int id) {
        attackerElements.get(id).hideSlider(MULTIPLIER_SLIDER);
    }

    private void updateNumberChange(int ID, Number newNumber) {
        if (Math.abs(newNumber.doubleValue() - newNumber.intValue()) < THRESHOLD) {
            observers.forEach(observer -> observer.updateNumberChange(ID, attackerElements.get(ID).getVal(TIME_SLIDER), newNumber.intValue()));
        }
    }

    private void updateTimeChange(int ID, Number newTime) {
        if (Math.abs(newTime.doubleValue() - newTime.intValue()) < THRESHOLD) {
            observers.forEach(observer -> observer.updateTimeChange(ID, newTime.intValue()));
        }
    }

    public void updateDisplayedAttackers(Map<Integer, Boolean> availabilityMap) {
        availabilityMap.forEach((ID, bool) -> {
            if (!bool && attackersBox.getChildren().contains(attackerElements.get(ID).getNode())) {
                attackersBox.getChildren().remove(attackerElements.get(ID).getNode());
            } else if (bool && !attackersBox.getChildren().contains(attackerElements.get(ID).getNode())) {
                attackersBox.getChildren().add(attackerElements.get(ID).getNode());
            }
        });
    }

    public void updateMaxTime(int ID, int maxTime) {
        this.maxTime = maxTime;
        attackerElements.get(ID).setMaxVal(TIME_SLIDER, maxTime);
    }

    public void updateMaxTime(int maxTime) {
        this.maxTime = maxTime;
    }

    public void updateSpecificFlow(int ID, int time, int amount) {
        attackerElements.get(ID).setVal(TIME_SLIDER, time);
        attackerElements.get(ID).setVal(NUMBER_SLIDER, amount);
    }

    public int getCurrentTime(int ID) {
        return attackerElements.get(ID).getVal(TIME_SLIDER);
    }

    public int getFlowMultiplier(int ID) {
        return attackerElements.get(ID).getVal(MULTIPLIER_SLIDER);
    }

    public void updateFlowMultiplier(int ID, int multiplier) {
        attackerElements.get(ID).setVal(MULTIPLIER_SLIDER, multiplier);
    }
}
