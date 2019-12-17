package networking;

import gameengineandplayerexamples.Game;

public interface Multiplayer extends NetworkedItem {

    /**
     * Updates the current game's state based on what opponent said is most up to date state
     *
     * @param game Game object with all items in most up to date state
     */
    void updateOpponentState(Game game);

    /**
     * Sends the most up to date state of this voogasalad.player's game
     *
     * @param game Game object with most up to date state
     */
    void sendCurrentState(Game game);
}
