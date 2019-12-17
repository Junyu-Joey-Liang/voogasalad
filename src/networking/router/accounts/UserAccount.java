package networking.router.accounts;

import networking.router.Account;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is responsible for keeping track of an account's information. It includes a username, password, and list of games with their respective scores.
 * This class allows one to store and retrieve information about an account. It is mainly a passive class because its purpose is to be sent over the network
 * to a server which then uses the account information to perform actions such as setting a high score or allowing a user to login.
 */
public class UserAccount implements Account, Serializable {
    private String username;
    private String password;
    private Map<String, Integer> gameScores;

    /**
     * Constructor for UserAccount class. This does not take in any values, but initializes all the private variables
     */
    public UserAccount() {
        username = "";
        password = "";
        gameScores = new HashMap<>();
    }

    /**
     * Returns the username held by the account.
     *
     * @return String value representing username of account
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets both the username and the password for an account. If the account does not require a password, then the password can just be set to ""
     *
     * @param username String value representing the username for the account
     * @param password String value representing the password for the current account
     */
    public void setUsernameAndPassword(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Allows for retrieval of the account's games and the games' associated scores.
     *
     * @return Map of strings to integers where strings are game names and integers are game scores
     */
    public Map<String, Integer> getScores() {
        return gameScores;
    }

    /**
     * Allows for retrieval of the score associated with a specific game
     *
     * @param game String name of game whose score is desired
     * @return Integer value representing score for that game
     */
    public int getScore(String game) {
        if (gameScores.containsKey(game)) {
            return gameScores.get(game);
        }
        return -1;
    }

    /**
     * Allows for associating a game with a certain score. Can be used to set high score, low score, most recent score, etc.
     *
     * @param game String name of game whose score is being stored
     * @param score Integer value being associated with said game
     */
    public void addScore(String game, int score) {
        gameScores.put(game, score);
    }

    /**
     * Allows for retrieval of password. This is useful when a client is communicating with a server and they two need to exchange passwords
     *
     * @return String value representing the password for this account
     */
    public String getPassword() {
        return password;
    }
}
