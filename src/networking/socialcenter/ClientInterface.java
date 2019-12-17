package networking.socialcenter;

import networking.router.Account;
import networking.router.messages.ServerMessage;
import networking.socialcenter.accountdisplay.AccountDisplayPage;
import networking.socialcenter.signin.SignInPage;

import java.util.List;

/**
 * Client application interface. Responsible for determining how the display pieces and networked pieces will interact.
 */
public interface ClientInterface {

    /**
     * Called to go from displaying a sign in page to logging into and displaying a current account.
     *
     * @param account Account to be set as currently logged in account.
     * @param threads List of String representing currently available threads.
     */
    void loginToAccount(Account account, List<String> threads);

    /**
     * Allows for retrieval of the SignInPage
     *
     * @return SignInPage being held by the social center.
     */
    SignInPage getSignInPage();

    /**
     * Allows for retrieval of account display page. Called by networked classes to make command design pattern changes to display
     * @return
     */
    AccountDisplayPage getAccountDisplayPage();

    /**
     * Called by display classes to send messages over the newtork via command classes
     *
     * @param message ServerMessage message to be sent from client to server.
     */
    void sendMessage(ServerMessage message);

    /**
     * Used to sign out the current user and return to the sign in/up screen.
     */
    void signOut();

    /**
     * Used to display an error to the user. Usually called by networked objects when an interaction between client and server
     * went wrong.
     *
     * @param message String message to be displayed to user.
     */
    void displayErrorMessage(String message);
}
