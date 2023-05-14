package common;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class represents a text message in a chat application. Each text message is associated with
 * a sender (RegisteredUser) and the message content is stored as a String.
 *
 * @author Shazhaib Kazmi
 */
public class TextMessage implements Message, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String text;

    private final RegisteredUser sender;

    /**
     * Constructs a TextMessage with the specified builder.
     *
     * @param textMessageBuilder the builder to construct a text message
     */
    private TextMessage(TextMessageBuilder textMessageBuilder){
        this.text= textMessageBuilder.text;
        this.sender= textMessageBuilder.sender;
    }

    /**
     * This class provides a way to build a TextMessage.
     */
    public static class TextMessageBuilder{
        private String text;
        private RegisteredUser sender;

        /**
         * Sets the text for the TextMessage to be built.
         *
         * @param text the text content
         * @return this builder
         */
        public TextMessageBuilder text (String text){
            this.text = text;
            return this;
        }

        /**
         * Sets the sender for the TextMessage to be built.
         *
         * @param sender the sender of the text message
         * @return this builder
         */
        public TextMessageBuilder sender(RegisteredUser sender) {
            this.sender = sender;
            return this;
        }

        /**
         * Returns a new TextMessage built from this builder.
         *
         * @return a new TextMessage
         */
        public TextMessage build(){
            return new TextMessage(this);
        }
    }

    /**
     * Returns the text content of the TextMessage.
     *
     * @return the text content
     */
    public String getText(){
        return text;
    }

    /**
     * Returns the sender of the TextMessage.
     *
     * @return the sender
     */
    @Override
    public RegisteredUser getSender() {
        return sender;
    }
}
