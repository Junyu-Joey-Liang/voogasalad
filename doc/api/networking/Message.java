package networking;

public interface Message {

    /**
     * Adds content to the message
     *
     * @param content String body that the message will hold
     */
    void addContent(Object content);

    String getContent();

    /**
     * Adds the sender to be used for an internal routing table so that next routing table can know from where this message came.
     *
     * @param senderClass Class of sender networked item
     */
    void addSender(Class senderClass);

    Class getSender();
}
