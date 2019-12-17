package networking.router.messages.clientmessages;

import networking.router.Account;
import networking.router.messages.ClientMessage;
import networking.socialcenter.ClientInterface;

import java.io.Serializable;
import java.util.List;

/**
 * Message sent from server to client to tell the client that the account credentials were valid and that it should login to the given account.
 * This is sent in response to both create account and login queries in which value credentials were given.
 */
public class UpdateSignInPageMessage implements ClientMessage, Serializable {
    private Account account;
    private List<String> threads;

    /**
     * Constructor for updateSignInPageMessage
     *
     * @param account Account object (either new or retrieved from a database) which will be eventually given to the client
     * @param threads List of all available threads on which the user can send messages
     */
    public UpdateSignInPageMessage(Account account, List<String> threads) {
        this.account = account;
        this.threads = List.copyOf(threads);
    }

    /**
     * Execute method for command object. Tells the client to login to the given account and store the threads on which the user can send
     * messages.
     *
     * @param clientInterface Client application interface on which the command will be executed.
     */
    @Override
    public void execute(ClientInterface clientInterface) {
        clientInterface.loginToAccount(account, threads);
    }
}
