package voogasalad.authoring.util.elementbuilders;

import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import voogasalad.authoring.util.elementbuilders.resources.ElementBuilderConstants;

import java.util.Iterator;

public class InputIntegerSliderElement {
    private static final String CSS_TAG = "slider-element";
    private static final String TEXT_FIELD_CSS_TAG = "slider-text-field-element";
    private static final int DEFAULT_PREF_WIDTH = 100;
    private static final String DIGIT_REGEX = "\\d+";
    private static final String BLANK_REGEX = "";
    private HBox mainNode;

    private int minValue;
    private int maxValue;
    private Slider mySlider;
    private TextField myTextField;
    private Label myLabel;

    public InputIntegerSliderElement(String label, int minValue, int maxValue, int defaultValue, ChangeListener listener, int prefWidth) {
        mainNode = new HBox();
        mainNode.getStylesheets().add(getClass().getResource(ElementBuilderConstants.ELEMENT_BUILDER_CSS_FILE).toExternalForm());
        mainNode.getStyleClass().add(CSS_TAG);

        this.minValue = minValue;
        this.maxValue = maxValue;
        myLabel = new Label(label);
        myTextField = new TextField(Integer.toString(defaultValue));
        myTextField.getStyleClass().add(TEXT_FIELD_CSS_TAG);
        mySlider = new Slider(minValue, maxValue, defaultValue);
        if (listener != null) {
            mySlider.valueProperty().addListener(listener);
        }
        mySlider.valueProperty().addListener((obs, old, neww) -> sliderValUpdate(neww, old));
        myTextField.textProperty().addListener((observable, oldValue, newValue) -> textFieldValUpdate(newValue, oldValue));
        mySlider.setPrefWidth(prefWidth);


        mainNode.getChildren().addAll(myLabel, mySlider, myTextField);

    }

    public InputIntegerSliderElement(String label, int minValue, int maxValue, int defaultValue, ChangeListener listener) {
        this(label, minValue, maxValue, defaultValue, listener, DEFAULT_PREF_WIDTH);
    }

    public InputIntegerSliderElement(int min, int max, int defaultValue, String label, String info, int preWidth) {
        this(label, min, max, defaultValue, null, preWidth);
        myLabel.setTooltip(new Tooltip(info));
    }

    public InputIntegerSliderElement(Iterator<String> inputIterator) {
        this(Integer.parseInt(inputIterator.next()), Integer.parseInt(inputIterator.next()), Integer.parseInt(inputIterator.next()), inputIterator.next(), inputIterator.next(), Integer.parseInt(inputIterator.next()));
    }

    public Slider getMySlider() {
        return mySlider;
    }

    private void sliderValUpdate(Number neww, Number old) {
        if (neww.equals(old)) {
            return;
        }
        int newVal = neww.intValue();
        myTextField.setText(Integer.toString(newVal));
    }

    private void textFieldValUpdate(String newValue, String oldValue) {
        if (newValue.equals(oldValue)) {
            return;
        }
        if (newValue.matches(DIGIT_REGEX) && Integer.parseInt(newValue) >= minValue && Integer.parseInt(newValue) <= maxValue) {
            mySlider.setValue(Integer.parseInt(newValue));
        } else if (newValue.matches(BLANK_REGEX)) {
            mySlider.setValue(minValue);
            myTextField.setText(Integer.toString(minValue));
        } else if (newValue.matches(DIGIT_REGEX) && Integer.parseInt(newValue) > maxValue) {
            mySlider.setValue(maxValue);
            myTextField.setText(Integer.toString(maxValue));
        } else {
            myTextField.setText(oldValue);
        }
    }

    public Node getNode() {
        return mainNode;
    }

    public int getVal() {
        return (int) mySlider.getValue();
    }

    public void setVal(int val) {
        mySlider.setValue(val);
    }

    public void setMaxVal(int maxVal) {
        this.maxValue = maxVal;
        mySlider.setMax(maxVal);
    }

    public void setId(String id) {
        mySlider.setId(id);
    }
}