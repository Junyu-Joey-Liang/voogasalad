package networking.router;

import java.util.Map;

/**
 * Interface for account object used to send account data between a client and server.
 */
public interface Account {

    /**
     * Returns the username held by the account.
     *
     * @return String value representing username of account
     */
    String getUsername();

    /**
     * Sets both the username and the password for an account. If the account does not require a password, then the password can just be set to ""
     *
     * @param username String value representing the username for the account
     * @param password String value representing the password for the current account
     */
    void setUsernameAndPassword(String username, String password);

    /**
     * Allows for retrieval of the account's games and the games' associated scores.
     *
     * @return Map of strings to integers where strings are game names and integers are game scores
     */
    Map<String, Integer> getScores();

    /**
     * Allows for retrieval of the score associated with a specific game
     *
     * @param game String name of game whose score is desired
     * @return Integer value representing score for that game
     */
    int getScore(String game);

    /**
     * Allows for associating a game with a certain score. Can be used to set high score, low score, most recent score, etc.
     *
     * @param game String name of game whose score is being stored
     * @param score Integer value being associated with said game
     */
    void addScore(String game, int score);

    /**
     * Allows for retrieval of password. This is useful when a client is communicating with a server and they two need to exchange passwords
     *
     * @return String value representing the password for this account
     */
    String getPassword();
}
