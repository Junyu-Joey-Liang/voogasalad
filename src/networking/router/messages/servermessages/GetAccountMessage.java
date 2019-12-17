package networking.router.messages.servermessages;

import networking.router.messages.ClientMessage;
import networking.router.messages.ServerMessage;
import networking.router.messages.clientmessages.ClientErrorMessage;
import networking.router.messages.clientmessages.UpdateSignInPageMessage;
import server.Server;

import java.io.Serializable;

/**
 * Message sent from client to server to request an account's information.
 */
public class GetAccountMessage implements ServerMessage, Serializable {
    private String username;
    private String password;

    /**
     * Constructor for GetAccountMessage
     *
     * @param username String username of account which is being retrieved
     * @param password String password of account which is being retrieved
     */
    public GetAccountMessage(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Execute method for command object. Tells the server to get the account information with given username and password. Server
     * sends back message to client to login with given account. In event there is an error, server sends the client an error message.
     *
     * @param server Server interface on which commands are executed.
     * @param threadID Thread ID (CPU thread not chat thread) on which client is communicating with server
     */
    @Override
    public void execute(Server server, int threadID) {
        ClientMessage clientMessage;
        try {
            clientMessage = new UpdateSignInPageMessage(server.getAccountManager().getAccount(username, password), server.getChatManager().getThreads());
        } catch (Exception e) {
            clientMessage = new ClientErrorMessage(e.getMessage());
        }
        server.sendMessage(clientMessage, threadID);
    }
}
