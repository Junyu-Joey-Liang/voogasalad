package voogasalad.player;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import voogasalad.player.setting.SettingImage;

public class GameMainWindow {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 750;
    private static final int POPUP_X = 250;
    private static final int POPUP_Y = 295;
    private static final String COIN = "Coin";

    private AnchorPane gameMainPane;
    private IMediator mediator;
    private DefenderTab defenderTab;
    private GameStatusDisplay myStatusDisplay;
    private GamePlayScreen myPlayScreen;
    private SettingImage mySettingImage;

    public GameMainWindow(IMediator mediator) {
        gameMainPane = new AnchorPane();
        gameMainPane.setPrefSize(WIDTH, HEIGHT);

        this.mediator = mediator;
        mediator.registerGameMainWindow(this);
        initialize();
    }

    public Pane getGamePane() {
        return gameMainPane;
    }

    public void setStatus(String statusType, String value) {
        myStatusDisplay.setStatus(statusType, value);
        if(statusType.equals(COIN)) {
            defenderTab.setCoin(Integer.parseInt(value));
        }
    }

    public void addToDefenderTab(String filePath, String defenderType, int cost) {
        defenderTab.addDefenderBlock(filePath, defenderType, cost);
        myStatusDisplay.getNode().toFront();
        mySettingImage.getNode().toFront();
    }

    public String getPressedDefender() {
        return defenderTab.getPressedDefender();
    }

    public void setDefenderReleased() {
        defenderTab.dragReleased();
    }

    public GamePlayScreen getGamePlayScreen() {
        return myPlayScreen;
    }

    public void endOfGameAction(String directory) {
        EndOfGameScreen endOfGameScreen = new EndOfGameScreen(directory, mediator);
        getGamePane().getChildren().add(endOfGameScreen.getNode());
        endOfGameScreen.getNode().setLayoutX(POPUP_X);
        endOfGameScreen.getNode().setLayoutY(POPUP_Y);
        mediator.stopGame();
    }

    public void newLevel() {
        defenderTab.clearDefenderBlock();
        myPlayScreen.getPane().getChildren().clear();
    }

    public void updateScore() {
        int score = myStatusDisplay.getStatus("Score");
        mediator.sendScore(score);
    }

    public void showSettingPopup() {
        mySettingImage.showSettingPopUp();
    }

    private void initialize() {
        defenderTab = new DefenderTab();
        myStatusDisplay = new GameStatusDisplay();
        myPlayScreen = new GamePlayScreen();
        mySettingImage = new SettingImage(mediator);
        gameMainPane.getChildren().addAll(myPlayScreen.getPane(), mySettingImage.getNode(), myStatusDisplay.getNode(), defenderTab.getNode());
    }
}
