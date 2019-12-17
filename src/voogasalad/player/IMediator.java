package voogasalad.player;


import voogasalad.MainScreen;
import voogasalad.player.gameselect.GameSelectScreen;
import voogasalad.player.setting.SettingPopup;

public interface IMediator {

    void registerSelectScreen(GameSelectScreen gameSelectScreen);

    void registerGameMainWindow(GameMainWindow gameMainWindow);

    void registerMainScreen(MainScreen mainScreen);

    void registerPopup(SettingPopup popup);

    void changeGameAction(String filePath);

    void startGame();

    void saveGame();

    void playGame();

    void stopGame();

    void exitGame();

    void newGame(boolean level);

    void loadGame(String filePath);

    void nextLevel();

    void sendScore(int score);

    void shutGame();

}
