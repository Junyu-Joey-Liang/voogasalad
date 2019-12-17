package voogasalad.authoring.util.elementbuilders;

import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static voogasalad.authoring.util.VisConstant.SPACING;

public class AuthoringChoice {

    private ChoiceBox choiceBox;
    private HBox myHBox;

    public AuthoringChoice(String label, String[] choices) {
        choiceBox = new ChoiceBox(FXCollections.observableArrayList(choices));
        if (choices.length>0) choiceBox.setValue(choices[0]);
        Label myLabel = new Label();
        myLabel.setText(label);
        myHBox = new HBox();
        myHBox.setSpacing(SPACING);
        myHBox.getChildren().addAll(myLabel, choiceBox);
    }

    public AuthoringChoice(Iterator<String> inputIterator) {
        this(inputIterator.next(),convertIteratorToArray(inputIterator));
    }

    private static String[] convertIteratorToArray(Iterator<String> iterator){
        List<String> list = new ArrayList<>();
        iterator.forEachRemaining(list::add);
        return list.toArray(new String[list.size()]);
    }



    public ChoiceBox getChoiceBox() {
        return choiceBox;
    }

    public HBox getHBox() {
        return myHBox;
    }
}
