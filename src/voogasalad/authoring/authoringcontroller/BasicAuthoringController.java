package voogasalad.authoring.authoringcontroller;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import voogasalad.authoring.controller.Controller;
import voogasalad.authoring.model.BasicModel;
import voogasalad.authoring.model.Model;
import voogasalad.authoring.view.AuthoringView;
import voogasalad.authoring.view.BasicAuthoringView;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import static voogasalad.authoring.util.VisConstant.RESOURCES_PATH;

/**
 * Implementation of {@link AuthoringController}, act as the central control for all controllers in GAE
 * Initializes all controller, attacher their nodes to display, and attack observers for each of them (all specified in a properties file)
 * (reason to include this class is specified in {@link voogasalad.authoring.controller.gameobjectsbuilder.attacker.AttackerController}
 */
public class BasicAuthoringController implements AuthoringController {
    private static final ResourceBundle CONTROLLERS_RB = ResourceBundle.getBundle(RESOURCES_PATH + "controllers");
    private static final String ERROR_INITIALISATION_MSG = ResourceBundle.getBundle(RESOURCES_PATH + "errorMessages").getString("ControllerInitialisationError");

    private AuthoringView myView;
    private Map<String, Controller> myControllers;
    private Model model;

    /**
     * Constructor. Initializes the view, controllers, and model.
     */
    public BasicAuthoringController() {
        myView = new BasicAuthoringView();
        myControllers = new HashMap<>();
        model = new BasicModel();
        initControllers();
    }

    /**
     * Return the node from view to display.
     * @return node of BasicAuthoringView
     */
    @Override
    public Node getView() {
        return myView.getNode();
    }

    private void initControllers() { // initialize the controllers from properties file, and attach observers for each accordingly
        CONTROLLERS_RB.getKeys().asIterator().forEachRemaining(key -> loadController(key, CONTROLLERS_RB.getString(key).split(",")[0]));
        attachObservers();
    }

    private void loadController(String controllerName, String classPath) { // use reflection to initialize each controller object, add their nodes to view, and all to myControllers list
        try {
            Class<?> clazz = Class.forName(classPath);
            Controller controller = (Controller) clazz.getDeclaredConstructor(Model.class).newInstance(model);
            myView.addNode(controller.getName(), controller.getNode());
            myControllers.put(controllerName, controller);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, ERROR_INITIALISATION_MSG).showAndWait();
        }
    }

    private void attachObservers() { // attach observers for each controller
        myControllers.keySet().forEach(controller -> {
            String allObservers = CONTROLLERS_RB.getString(controller).split(",")[1];
            if (!allObservers.isBlank()) {
                String[] observerList = allObservers.split(";");
                for (String observer : observerList) {
                    if (myControllers.containsKey(observer)) {
                        myControllers.get(controller).addObserver(myControllers.get(observer));
                    }
                }
            }
        });
    }

}
