package voogasalad.player;

import networking.socialcenter.SocialCenter;
import voogasalad.MainScreen;
import voogasalad.data.Data;
import voogasalad.data.GameData;
import voogasalad.gameengine.enginecontroller.externalengine.ExternalEngineInterface;
import voogasalad.gameengine.enginecontroller.externalengine.TowerDefenseExternalEngine;
import voogasalad.gameengine.game.Game;
import voogasalad.player.gameselect.GameSelectScreen;
import voogasalad.player.setting.SettingPopup;

public class PlayerMediator implements IMediator {
//    private GameStartScreen gameStartScreen;
    private GameSelectScreen gameSelectScreen;
    private GameMainWindow gameMainWindow;
    private String originalFilePath;
    private ExternalEngineInterface externalEngine;
    private SettingPopup settingPopup;
    private MainScreen mainScreen;
    private SocialCenter socialCenter;

    public PlayerMediator() {

    }

    public PlayerMediator(SocialCenter socialCenter) {
        this.socialCenter = socialCenter;
    }

    @Override
    public void registerMainScreen(MainScreen mainScreen) {
        this.mainScreen = mainScreen;
    }

    @Override
    public void registerSelectScreen(GameSelectScreen gameSelectScreen) {
        this.gameSelectScreen = gameSelectScreen;
    }

    @Override
    public void registerGameMainWindow(GameMainWindow gameMainWindow) {
        this.gameMainWindow = gameMainWindow;
    }

    @Override
    public void registerPopup(SettingPopup settingPopup) {
        this.settingPopup = settingPopup;
    }

    @Override
    public void changeGameAction(String filePath) {
        originalFilePath = filePath;
        gameMainWindow = new GameMainWindow(this);
        externalEngine = new TowerDefenseExternalEngine(gameMainWindow, filePath);
        // gameMainWindow.getGameStage().show();
        mainScreen.getPlayerTab().setContent(gameMainWindow.getGamePane());
        //playGame();
    }

    @Override
    public void startGame() {
        System.out.println("sdfsdfsdf");
    }

    @Override
    public void saveGame() {
        Game game = externalEngine.saveGame();
        Data data = new GameData(originalFilePath);
        data.saveGame(game);
        externalEngine.play();
    }

    @Override
    public void playGame() {
        externalEngine.play();
    }

    @Override
    public void stopGame() {
        externalEngine.stop();
    }

    @Override
    public void loadGame(String filePath) {
        externalEngine.loadGame(filePath);
        //playGame();
    }

    @Override
    public void exitGame() {
        mainScreen.getPlayerTab().setContent(gameSelectScreen.getNode());
    }

    @Override
    public void newGame(boolean level) {
        externalEngine.newGame(level);
    }

    @Override
    public void nextLevel() {
        externalEngine.nextLevel();
        externalEngine.play();
    }

    @Override
    public void sendScore(int score) {
        Data data = new GameData(originalFilePath);
        Game game = data.retrieveGame();
        String title = game.getTitle();
        socialCenter.updateHighScore(title, score);
    }

    @Override
    public void shutGame() {
        GameSelectScreen newGame = new GameSelectScreen(this);
        mainScreen.getPlayerTab().setContent(newGame.getNode());
    }
}
