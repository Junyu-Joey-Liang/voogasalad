package voogasalad.player.gameselect;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import voogasalad.player.IMediator;
import voogasalad.player.playercommand.ChangeGameCommand;
import voogasalad.player.playercommand.ICommand;

public class GameLibraryDisplay extends FlowPane {
    private static final String GAME_AREA = "game-area";
    private static final String GAME_DIRECTORY = System.getProperty("user.dir") + "/data/games";
    private static final double H_GAP = 10;
    private static final double V_Gap = 10;
    private static final double PADDING = 5;

    private GameLibrary myGameLibrary;
    private IMediator myController;

    public GameLibraryDisplay(IMediator controller) {
        super();
        myGameLibrary = new GameLibrary(GAME_DIRECTORY);
        myController = controller;
        initialize();
        getStyleClass().add("game-display");
    }

    public void refresh() {
        myGameLibrary.refreshGamelibrary();
        getChildren().clear();
        createGameAreas();
    }

    private void initialize() {
        setHgap(H_GAP);
        setVgap(V_Gap);
        setPadding(new Insets(PADDING));
        setAlignment(Pos.TOP_CENTER);
        createGameAreas();
    }

    public void createGameAreas() {
        for(GameInformation gameInformation : myGameLibrary.getGameInformationList()) {
            VBox gameArea = new GameArea(gameInformation);
            gameArea.getStyleClass().add(GAME_AREA);
            gameArea.setOnMouseClicked(e -> selectGame(gameInformation.getFilePath()));
            getChildren().add(gameArea);
        }
    }

    private void selectGame(String filePath) {
        ICommand playerCommand = new ChangeGameCommand(myController, filePath);
        playerCommand.execute();
    }
}
