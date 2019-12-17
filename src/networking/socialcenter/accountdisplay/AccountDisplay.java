package networking.socialcenter.accountdisplay;

/**
 * Interface for account display page.
 */
public interface AccountDisplay {

    /**
     * Tells the account display page to join a given thread.
     *
     * @param threadName String name of thread the user wishes to join.
     */
    void joinThread(String threadName);

    /**
     * Tells the account display page that the user is trying to send a message.
     *
     * @param message String message content.
     * @param chat String name of chat on which user sent message
     */
    void sendMessage(String message, String chat);

    /**
     * Allows for retrieval of username
     *
     * @return String value of username.
     */
    String getUsername();
}
