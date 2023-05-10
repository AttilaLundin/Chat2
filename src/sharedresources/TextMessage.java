package sharedresources;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.interfaces.DataHandler;
import sharedresources.interfaces.Message;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class TextMessage implements Message, DataHandler, Serializable {
    private final String text;
    private String timeSent;
    private User sender;
    private UUID chatRoomID;

    private TextMessage(TextMessageBuilder textMessageBuilder){
        this.text= textMessageBuilder.text;
        this.timeSent = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.sender= textMessageBuilder.sender;
    }

    // new TextMessage.TextMessageBuilder().text("abx").sender(user).build();

    public static class TextMessageBuilder{
        private String text;
        private String timeSent;
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
    public String getTimeSent() {
        return timeSent;
    }

    @Override
    public User getSender() {
        return sender;
    }

    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatRoomStorage, ObjectOutputStream outputStream) {

    }
}
