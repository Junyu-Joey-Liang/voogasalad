package testing.networktesting;

import networking.router.Account;
import networking.router.accounts.UserAccount;
import org.junit.jupiter.api.Test;
import server.managers.AccountManager;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTest {
    private static final String username = "username";
    private static final String password = "password";

    private AccountManager accountManager;

    public AccountManagerTest() {
         accountManager = new AccountManager();
    }

    @Test
    void addAccount() {
        addDummyAccount();
        Account newAccount = accountManager.getAccount(username, password);
        assertEquals(username, newAccount.getUsername());
        assertEquals(password, newAccount.getPassword());
        resetAccountManager();
    }

    @Test
    void getAccount() {
        String username = "username";
        String password = "password";
        assertThrows(Exception.class, () -> accountManager.getAccount(username, password));
        resetAccountManager();
    }

    @Test
    void setUserHighscore() {
        addDummyAccount();
        String game = "game";
        int highScore = 20;
        accountManager.setUserHighscore(username, highScore, game);
        assertEquals(highScore, accountManager.getUserHighscore(username, game));
        resetAccountManager();
    }

    @Test
    void getUserHighscore() {
        addDummyAccount();
        String game = "game";
        int highScore = 20;
        int lowScore = 10;
        accountManager.setUserHighscore(username, highScore, game);
        accountManager.setUserHighscore(username, lowScore, game);
        assertNotEquals(lowScore, accountManager.getUserHighscore(username, game));
        resetAccountManager();
    }

    @Test
    void changePassword() {
        addDummyAccount();
        String updatedPassword = "updatedPassword";
        accountManager.changePassword(username, password, updatedPassword);
        assertNotEquals(password, accountManager.getAccount(username, updatedPassword).getPassword());
        assertEquals(updatedPassword, accountManager.getAccount(username, updatedPassword).getPassword());
        resetAccountManager();
    }

    private void addDummyAccount() {
        Account account = new UserAccount();
        account.setUsernameAndPassword(password, username);
        accountManager.addAccount(username, password);
    }

    private void resetAccountManager() {
        accountManager = new AccountManager();
    }
}