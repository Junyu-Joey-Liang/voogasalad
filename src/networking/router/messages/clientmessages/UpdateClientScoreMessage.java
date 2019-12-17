package networking.router.messages.clientmessages;

import networking.router.messages.ClientMessage;
import networking.socialcenter.ClientInterface;
import networking.socialcenter.accountdisplay.AccountDisplayPage;

import java.io.Serializable;

/**
 * Message sent from server to client to tell the client to update its score being displayed for a given game. This can be used to tell the client to display
 * a high score, low score, etc.
 */
public class UpdateClientScoreMessage implements ClientMessage, Serializable {
    private String game;
    private int score;

    /**
     * Constructor for UpdateClientScoreMessage
     *
     * @param game String name of game whose score is to be updated
     * @param score Integer value of score for said game
     */
    public UpdateClientScoreMessage(String game, int score) {
        this.game = game;
        this.score = score;
    }

    /**
     * Execute method for command object. Tells the client to update the score on the account page for the given game with the new score.
     *
     * @param clientInterface Client application interface on which the new score will be updated.
     */
    @Override
    public void execute(ClientInterface clientInterface) {
        AccountDisplayPage accountDisplayPage = clientInterface.getAccountDisplayPage();
        accountDisplayPage.addScore(game, score);
    }
}