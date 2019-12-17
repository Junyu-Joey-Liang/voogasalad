package networking.router.messages.servermessages;

import networking.router.messages.ServerMessage;
import server.Server;

import java.io.Serializable;

/**
 * Message sent from client to server to tell the server that a given user is signing out.
 */
@Deprecated
public class SignOutMessage implements ServerMessage, Serializable {

    /**
     * Tells the server that the given thread id (CPU thread not chat thread) will no longer be communicating with the server
     *
     * @param server Server interface on which command is executed
     * @param threadID Integer thread ID (CPU thread not server thread) on which server and client are communicating.
     */
    @Override
    public void execute(Server server, int threadID) {
        server.signOutUser(threadID);
    }
}
