package sharedresources;

import sharedresources.interfaces.DataHandler;
import sharedresources.interfaces.Message;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class TextMessage implements Message, DataHandler, Serializable {
    private final String text;
    private String timeSent;
    private SessionUser sender;
    private UUID chatRoomID;

    public TextMessage(TextMessageBuilder textMessageBuilder){
        this.text= textMessageBuilder.text;
        this.timeSent = textMessageBuilder.timeSent;
        this.sender= textMessageBuilder.sender;
        this.chatRoomID = textMessageBuilder.chatRoomID;
    }

    public static class TextMessageBuilder{
        private String text;
        private String timeSent;
        private SessionUser sender;
        private UUID chatRoomID;

        public TextMessageBuilder text (String text){
            this.text = text;
            return this;
        }

        public TextMessageBuilder timeSent() {
            this.timeSent = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            return this;
        }

        public TextMessageBuilder sender(SessionUser sender) {
            this.sender = sender;
            return this;
        }

        public TextMessageBuilder chatRoomID(UUID chatRoomID){
            this.chatRoomID = chatRoomID;
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
    public SessionUser getSender() {
        return sender;
    }

    public UUID getChatRoomID(){
        return chatRoomID;
    }

    @Override
    public void dataHandler(Object registeredUser, Object chatHistory, ObjectOutputStream outputStream) {

    }

    public void messageHandler(){

    }




}
