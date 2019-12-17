package voogasalad.authoring.util.elementbuilders;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import voogasalad.authoring.util.elementbuilders.resources.ElementBuilderConstants;

public class InputTextElement {
    private static final String CSS_TAG = "text-element";
    private static final String TEXT_FIELD_CSS_TAG = "text-field-element";
    private static final String EMPTY = "";
    private HBox mainNode;
    private TextField myTextField;

    public InputTextElement(String label, String defaultValue) {
        mainNode = new HBox();
        mainNode.getStylesheets().add(getClass().getResource(ElementBuilderConstants.ELEMENT_BUILDER_CSS_FILE).toExternalForm());
        mainNode.getStyleClass().add(CSS_TAG);
        myTextField = new TextField(defaultValue);
        myTextField.getStyleClass().add(TEXT_FIELD_CSS_TAG);
        Label myLabel = new Label(label);

        mainNode.getChildren().addAll(myLabel, myTextField);

    }

    public InputTextElement(String label) {
        this(label, EMPTY);
    }

    public Node getNode() {
        return mainNode;
    }

    public TextField getTextField() {
        return myTextField;
    }

    public String getText() {
        return myTextField.getText();
    }

    public void setText(String text) {
        myTextField.setText(text);
    }
}