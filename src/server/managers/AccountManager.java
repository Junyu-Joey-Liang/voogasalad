package server.managers;

import networking.router.Account;
import networking.router.accounts.UserAccount;
import server.errors.AccountError;

import java.util.HashMap;
import java.util.Map;

/**
 * Class responsible for handling accounts on the server.
 */
public class AccountManager extends Manager {
    private static final String USERNAME_ALREADY_EXISTS_ERROR_STRING = "Username already exists";
    private static final String INCORRECT_ACCOUNT_CREDENTIALS_ERROR_STRING = "Incorrect username or password";
    private static final String EMPTY_USERNAME_OR_PASSWORD_STRING = "Please enter a username and password";

    private Map<String, Account> accounts;

    /**
     * Constructor for AccountManager
     *
     * This takes in and returns no values.
     */
    public AccountManager() {
        accounts = new HashMap<>();
    }

    /**
     * Adds an account to the list of accounts on the server. This checks that the username and password are valid values.
     *
     * @param username String value of username.
     * @param password String value of password.
     * @return Account object with username, password, and games/scores
     */
    public Account addAccount(String username, String password) {
        if (accounts.containsKey(username)) {
            throw new AccountError(USERNAME_ALREADY_EXISTS_ERROR_STRING);
        }
        if (username.equals("") || password.equals("")) {
            throw new AccountError(EMPTY_USERNAME_OR_PASSWORD_STRING);
        }

        Account account = new UserAccount();
        account.setUsernameAndPassword(username, password);
        accounts.put(username, account);

        return account;
    }

    /**
     * Retrieves an account that already exist in the server.
     *
     * @param username String value of username.
     * @param password String value of password.
     * @return Account object with username, password, and game/scores
     */
    public Account getAccount(String username, String password) {
        throwErrorIfIncorrectPassword(username, password);
        return accounts.get(username);
    }

    /**
     * If the given high score is greater than the game's current high score then sets the game's high score to the new
     * score.
     *
     * @param username String username of user whose game's high score is being updated.
     * @param score Integer value of possible new high score.
     * @param gameID String name of game whose high score is possibly being updated.
     */
    public void setUserHighscore(String username, int score, String gameID) {
        throwErrorIfIncorrectUsername(username);

        if (accounts.get(username).getScore(gameID) < score) {
            accounts.get(username).addScore(gameID, score);
        }
    }

    /**
     * Retrieves high score of given game for given user.
     *
     * @param username String value of username.
     * @param gameID String name of game whose high score is requested.
     * @return Integer value of current high score for given game.
     */
    public int getUserHighscore(String username, String gameID) {
        throwErrorIfIncorrectUsername(username);
        return accounts.get(username).getScore(gameID);
    }

    /**
     * Changes the password from the account's current password to the new password. Throws error if
     * given current username and/or password are incorrect.
     *
     * @param username String value of username
     * @param oldPassword String value of current password
     * @param newPassword String value of new password.
     */
    public void changePassword(String username, String oldPassword, String newPassword) {
        throwErrorIfIncorrectPassword(username, oldPassword);
        accounts.get(username).setUsernameAndPassword(username, newPassword);
    }

    // TODO: Change these to take in lambdas
    private void throwErrorIfIncorrectUsername(String username) {
        if (!accounts.containsKey(username)) {
            throw new AccountError(INCORRECT_ACCOUNT_CREDENTIALS_ERROR_STRING);
        }
    }

    private void throwErrorIfIncorrectPassword(String username, String password) {
        throwErrorIfIncorrectUsername(username);

        if (!accounts.get(username).getPassword().equals(password)) {
            throw new AccountError(INCORRECT_ACCOUNT_CREDENTIALS_ERROR_STRING);
        }
    }
}
