package sharedresources;

import sharedresources.interfaces.Message;
import java.io.Serializable;


public class TextMessage implements Message, Serializable {
    private final String text;
    private User sender;

    private TextMessage(TextMessageBuilder textMessageBuilder){
        this.text= textMessageBuilder.text;
        this.sender= textMessageBuilder.sender;
    }

    public static class TextMessageBuilder{
        private String text;
        private User sender;

        public TextMessageBuilder text (String text){
            this.text = text;
            return this;
        }

        public TextMessageBuilder sender(User sender) {
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
    public User getSender() {
        return sender;
    }

}
