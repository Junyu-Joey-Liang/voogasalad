package voogasalad;

import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import networking.socialcenter.SocialCenter;
import voogasalad.authoring.authoringcontroller.AuthoringController;
import voogasalad.authoring.authoringcontroller.BasicAuthoringController;
import voogasalad.player.IMediator;
import voogasalad.player.PlayerMediator;
import voogasalad.player.gameselect.GameSelectScreen;

public class MainScreen {
    private static final String MAIN_TAB_PANE_ID = "MainTabPane";
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 800;

    private TabPane mainTab;
    private Stage mainStage;
    private SocialCenter socialCenter;
    private VBox mainPane;
    private Scene mainScene;
    private AuthoringController authoringController;
    private IMediator mediator;
    private GameSelectScreen gameSelectScreen;

    private Tab playerTab;
    private Tab socialTab;
    private Tab authoringTab;

    public MainScreen() {

        socialCenter = new SocialCenter();
        mediator = new PlayerMediator(socialCenter);
        authoringController = new BasicAuthoringController();
        mainPane = new VBox();
        gameSelectScreen = new GameSelectScreen(mediator);
        mainTab = new TabPane();

        mainPane.getChildren().add(mainTab);
        mainScene = new Scene(mainPane, WIDTH, HEIGHT);
        mainStage = new Stage();
        mainStage.setHeight(HEIGHT);
        mainStage.setScene(mainScene);

        mediator.registerMainScreen(this);

        initialize();
        mainStage.show();
    }

    private void initialize() {
        createTabs();
        setMainTab();
    }

    private void createTabs() {
        authoringTab = new Tab("Authoring Environment");
        authoringTab.setContent(authoringController.getView());
        socialTab = new Tab("Social Center");
        socialTab.setContent(socialCenter.getNode());
        playerTab = new Tab("Game Player");
        playerTab.setContent(gameSelectScreen.getNode());
    }

    private void setMainTab() {
        mainTab.setId(MAIN_TAB_PANE_ID);
        mainTab.getTabs().addAll(socialTab, authoringTab, playerTab);
        mainTab.setTabDragPolicy(TabPane.TabDragPolicy.REORDER);
        mainTab.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }


    public Tab getPlayerTab() {
        return playerTab;
    }
}
