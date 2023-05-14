package common;


/**
 * An interface that represents a message in the chat application.
 * This interface has a method to get the sender of a message.
 *
 * @author Attila Lundin
 */
public interface Message{

    /**
     * Retrieves the sender of a message.
     *
     * @return User object representing the sender of the message
     */
    RegisteredUser getSender();
}
