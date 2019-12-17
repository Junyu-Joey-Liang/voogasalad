package networking.router.messages;

import server.Server;

import java.io.Serializable;

/**
 * Interface for messages being sent from client to server. Follows command design pattern.
 */
public interface ServerMessage extends Serializable {

    /**
     * Execute method for command object.
     *
     * @param server Server interface on which commands will be executed.
     * @param threadID Integer CPU thread ID on which server communicates with client.
     */
    void execute(Server server, int threadID);
}
