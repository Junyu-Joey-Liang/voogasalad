package networking.router.messages.servermessages;

import networking.router.messages.ClientMessage;
import networking.router.messages.ServerMessage;
import networking.router.messages.clientmessages.ClientErrorMessage;
import networking.router.messages.clientmessages.UpdateSignInPageMessage;
import server.Server;

import java.io.Serializable;

/**
 * Message sent from client to server to tell the server to create an account.
 */
public class CreateAccountMessage implements ServerMessage, Serializable {
    private String username;
    private String password;

    /**
     * Constructor for CreateAccountMesage
     *
     * @param username String username for new account
     * @param password String password for new account
     */
    public CreateAccountMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Execute method for command object. Tells the server to create an account with the given username and password.
     * If there are any problems with this then it sends an error message back to the user. If no problems then tells the
     * user to login.
     *
     * @param server Server interface on which command is executed
     * @param threadID ID of thread (CPU thread) on which client is communicating with server
     */
    @Override
    public void execute(Server server, int threadID) {
        ClientMessage clientMessage;
        try {
            clientMessage = new UpdateSignInPageMessage(server.getAccountManager().addAccount(username, password), server.getChatManager().getThreads());
        } catch (Exception e) {
            clientMessage = new ClientErrorMessage(e.getMessage());
        }
        server.sendMessage(clientMessage, threadID);
    }
}
