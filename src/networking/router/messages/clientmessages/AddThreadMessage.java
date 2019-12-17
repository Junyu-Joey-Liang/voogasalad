package networking.router.messages.clientmessages;

import networking.router.messages.ClientMessage;
import networking.socialcenter.ClientInterface;
import networking.socialcenter.accountdisplay.AccountDisplayPage;

import java.io.Serializable;

/**
 * Command object that is sent from the server to the client. This class tells the client to add a thread to the list of available threads on which users
 * can chat. Note that thread here is referring to the string of chat messages seen by the user, not a CPU thread running independently in the code.
 */
public class AddThreadMessage implements ClientMessage, Serializable {
    private String thread;

    /**
     * Constructor for the AddThreadmessage
     *
     * @param threadName String name of thread on which users can chat
     */
    public AddThreadMessage(String threadName) {
        thread = threadName;
    }

    /**
     * Execute method for the command design pattern.
     *
     * @param client Takes in the client application interface and tells it to add a thread to the list of available threads
     */
    @Override
    public void execute(ClientInterface client) {
        AccountDisplayPage accountDisplayPage = client.getAccountDisplayPage();
        accountDisplayPage.addThread(thread);
    }
}