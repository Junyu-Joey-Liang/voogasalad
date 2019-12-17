package voogasalad.authoring.view;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class BasicAuthoringView implements AuthoringView {
    private static final String RESOURCES_PATH = "/voogasalad/authoring/resources/";
    private static final String VIEW_CSS_FILE = "defaultStylesheet.css";
    private static final String ID_TAB_PANE = "TABPANE";
    private TabPane tabPane;

    public BasicAuthoringView() {
        tabPane = new TabPane();
        tabPane.setId(ID_TAB_PANE);
        tabPane.getStylesheets().add(getClass().getResource(RESOURCES_PATH + VIEW_CSS_FILE).toExternalForm());
        tabPane.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    @Override
    public void addNode(String nodeName, Node node) {
        Tab newTab = new Tab(nodeName, node);
        newTab.setId(nodeName.replaceAll("\\s+", ""));
        tabPane.getTabs().add(newTab);
    }

    public Node getNode() {
        return tabPane;
    }
}
