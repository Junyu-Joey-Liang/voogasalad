package networking.router.messages.servermessages;

import networking.router.messages.ClientMessage;
import networking.router.messages.ServerMessage;
import networking.router.messages.clientmessages.UpdateClientScoreMessage;
import server.Server;
import server.managers.AccountManager;

import java.io.Serializable;

/**
 * Message sent from client to server to tell the server to tell the server to update a user's high score for a specific game.
 */
public class UpdateHighScoreMessage implements ServerMessage, Serializable {
    private String user;
    private String gameName;
    private int highscore;

    /**
     * Constructor for UpdateHigScoreMessage
     *
     * @param username String username of user whose score is being updated
     * @param game String name of game whose score is being updated
     * @param score Integer score value to possibly update the game to
     */
    public UpdateHighScoreMessage(String username, String game, int score) {
        user = username;
        gameName = game;
        highscore = score;
    }

    /**
     * Execute method for command object. Tells the server to set the new game score to the given score only in the event
     * that the new score is the highest score for the game. Then sends a return message to the client to update the client's
     * view with newest score.
     *
     * @param server Server interface on which commands will be executed
     * @param threadID Integer thread ID (CPU thread not chat thread) on which server and client are communicating
     */
    @Override
    public void execute(Server server, int threadID) {
        AccountManager accountManager = server.getAccountManager();
        accountManager.setUserHighscore(user, highscore, gameName);
        int score = accountManager.getUserHighscore(user, gameName);

        ClientMessage message = new UpdateClientScoreMessage(gameName, score);
        server.sendMessage(message, threadID);
    }
}