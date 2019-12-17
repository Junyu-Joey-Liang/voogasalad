package voogasalad.player.gameselect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameLibrary {

    private List<GameInformation> gameInformationList = new ArrayList<>();
    private String directory;

    public GameLibrary(String directory) {
        this.directory = directory;
        constructGameInformation();
    }

    public List<GameInformation> getGameInformationList() {
        ArrayList<GameInformation> list = new ArrayList<>();
        list.addAll(gameInformationList);
        return list;
    }

    public void refreshGamelibrary() {
        gameInformationList.clear();
        constructGameInformation();
    }

    private void constructGameInformation() {
        File[] files = new File(directory).listFiles();
        for(File file : files) {
            GameInformation gameInformation = new GameInformation(directory + "/" + file.getName());
            gameInformationList.add(gameInformation);
        }
    }
}
