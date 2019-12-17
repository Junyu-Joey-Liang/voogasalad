package networking.router.messages;

import networking.socialcenter.ClientInterface;

import java.io.Serializable;

/**
 * Interface for messages sent from the server to the client. These follow command design patter.
 */
public interface ClientMessage extends Serializable {

    /**
     * Execute method for command design pattern.
     *
     * @param clientInterface  Client application interface on which command will be executed.
     */
    void execute(ClientInterface clientInterface);
}