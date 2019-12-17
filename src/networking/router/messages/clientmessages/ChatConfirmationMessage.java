package networking.router.messages.clientmessages;

import networking.router.messages.ClientMessage;
import networking.socialcenter.accountdisplay.AccountDisplayPage;
import networking.socialcenter.ClientInterface;

import java.io.Serializable;
import java.util.List;

/**
 * Command message sent from the user to the client to confirm that the user has successfully been added to a chat.
 */
public class ChatConfirmationMessage implements ClientMessage, Serializable {
    private String thread;
    private List<String> history;

    /**
     * Constructor for ChatConfirmationMessage
     *
     * @param threadName String name of thread to which user has been added
     * @param history List of all the messages included in the thread thus far
     */
    public ChatConfirmationMessage(String threadName, List<String> history) {
        thread = threadName;
        this.history = List.copyOf(history);
    }

    /**
     * Execute method required for command objects
     *
     * @param client Takes in client application interface and tells it to set the current chat as the one given and adds the history
     *               in for that chat
     */
    @Override
    public void execute(ClientInterface client) {
        AccountDisplayPage accountDisplayPage = client.getAccountDisplayPage();
        accountDisplayPage.setCurrentChat(thread, history);
        accountDisplayPage.addChat(thread);
    }
}
