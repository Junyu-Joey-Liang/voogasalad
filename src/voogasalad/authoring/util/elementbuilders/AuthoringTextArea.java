package voogasalad.authoring.util.elementbuilders;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import voogasalad.authoring.util.VisConstant;

import java.util.Iterator;


public class AuthoringTextArea {
    private final double SPACING = Double.parseDouble(VisConstant.gameObjectBundle.getString("Spacing"));
    private final double WIDTH = 350;
    private TextArea textArea;
    private HBox myHBox;
//    private Button submitButton;

    public AuthoringTextArea(String label) {
        textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setMaxWidth(WIDTH);
//        submitButton = new Button("submit");
        Label myLabel = new Label();
        myLabel.setText(label);
        myHBox = new HBox(SPACING);
        myHBox.getChildren().addAll(myLabel, textArea);
    }

//    public AuthoringTextArea(Iterator<String> it) {
//        this(it.next());
//    }

    public HBox getHBox() {
        return myHBox;
    }

    public TextArea getTextArea() {
        return textArea;
    }

//    public Button getSubmitButton() {
//        return submitButton;
//    }
}