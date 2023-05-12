package common;

import java.io.Serial;
import java.io.Serializable;


public class TextMessage implements Message, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String text;
    private final RegisteredUser sender;

    private TextMessage(TextMessageBuilder textMessageBuilder){
        this.text= textMessageBuilder.text;
        this.sender= textMessageBuilder.sender;
    }

    public static class TextMessageBuilder{
        private String text;
        private RegisteredUser sender;

        public TextMessageBuilder text (String text){
            this.text = text;
            return this;
        }

        public TextMessageBuilder sender(RegisteredUser sender) {
            this.sender = sender;
            return this;
        }

        public TextMessage build(){
            return new TextMessage(this);
        }
    }


    public String getText(){
        return text;
    }

    @Override
    public RegisteredUser getSender() {
        return sender;
    }

}
