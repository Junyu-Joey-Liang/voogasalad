package voogasalad.authoring.util.elementbuilders;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import voogasalad.authoring.util.elementbuilders.resources.ElementBuilderConstants;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class MultipleIntegerSliderElement {
    private static final String ERROR_SLIDER_VIEW = "ErrorSliderView";
    private Map<String, InputIntegerSliderElement> inputIntegerSliderElementMap;
    private VBox mainNode;
    private int sliderWidth;
    private ResourceBundle errorMessages;

    public MultipleIntegerSliderElement(int sliderPrefWidth) {
        errorMessages = ResourceBundle.getBundle(ElementBuilderConstants.ERROR_MESSAGE_FILE);
        inputIntegerSliderElementMap = new HashMap<>();
        mainNode = new VBox();
        this.sliderWidth = sliderPrefWidth;
    }

    public void addSlider(String sliderName, String sliderLabel, int sliderMinVal, int sliderMaxVal, int sliderDefaultVal, ChangeListener listener) {
        InputIntegerSliderElement newSlider = new InputIntegerSliderElement(sliderLabel, sliderMinVal, sliderMaxVal, sliderDefaultVal, listener, sliderWidth);
        inputIntegerSliderElementMap.put(sliderName, newSlider);
        mainNode.getChildren().addAll(newSlider.getNode());
    }

    public int getVal(String sliderName) {
        return inputIntegerSliderElementMap.get(sliderName).getVal();
    }

    public void setVal(String sliderName, int val) {
        inputIntegerSliderElementMap.get(sliderName).setVal(val);
    }

    public void setMaxVal(String sliderName, int maxVal) {
        inputIntegerSliderElementMap.get(sliderName).setMaxVal(maxVal);
    }

    public Node getNode() {
        return mainNode;
    }

    public void hideSlider(String sliderName) {
        try {
            mainNode.getChildren().remove(inputIntegerSliderElementMap.get(sliderName).getNode());
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, errorMessages.getString(ERROR_SLIDER_VIEW)).showAndWait();
        }
    }

    public void showSlider(String sliderName) {
        try {
            Node sliderNode = inputIntegerSliderElementMap.get(sliderName).getNode();
            if (!mainNode.getChildren().contains(sliderNode)) {
                mainNode.getChildren().add(sliderNode);
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, errorMessages.getString(ERROR_SLIDER_VIEW)).showAndWait();
        }
    }
}
