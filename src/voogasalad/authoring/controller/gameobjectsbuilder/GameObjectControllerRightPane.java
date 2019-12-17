package voogasalad.authoring.controller.gameobjectsbuilder;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import voogasalad.authoring.authoringfeature.AuthoringFeature;
import voogasalad.authoring.util.StorageUtil;
import voogasalad.authoring.util.VisConstant;
import voogasalad.authoring.util.VisualizationUtil;

import java.util.Arrays;

/**
 * Right pane for game object controller tabs
 */
public class GameObjectControllerRightPane implements ControllerRightPane{
    private VBox pane;
    private AbstractGameObjectController controller;

    /**
     * initialize the right pane
     *
     * @param controller controller for this tab
     */
    public GameObjectControllerRightPane(AbstractGameObjectController controller){
        this.controller = controller;
        initPane();
    }

    private void initPane(){
        this.pane = VisualizationUtil.createVBox(Arrays.asList(VisConstant.gameObjectBundle.getString(controller.getType() + "rightCol").split(",")).iterator());
        Button saveCurrentObjButton = VisualizationUtil.makeButton("Save\nChanges", value -> controller.saveCurrentObj());
        Button addButton = VisualizationUtil.makeButton("Create New", value -> controller.addObject());
        Button saveToFileButton = VisualizationUtil.makeButton("Save To File", value -> saveToFile(controller.getCurrentObj(),value));
        Text info = new Text("Hover over the labels for\nexplanation of each parameter.");
        pane.getChildren().addAll(info,addButton,saveCurrentObjButton,saveToFileButton);
    }

    private void saveToFile(AuthoringFeature obj, ActionEvent e){
        if (controller.getCurrentObj()!=null){
            StorageUtil.saveAuthoringFeatureToFile(obj,e);
        }
    }

    /**
     * get the right pane's node
     *
     * @return right pane's node
     */
    public VBox getPane() {
        return pane;
    }
}
