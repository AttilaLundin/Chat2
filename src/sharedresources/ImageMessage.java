package sharedresources;

import sharedresources.interfaces.DataHandler;
import sharedresources.interfaces.Message;

import java.awt.image.BufferedImage;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class ImageMessage implements Message, DataHandler, Serializable {
    private final transient BufferedImage image;
    private final String timeSent;
    private final User sender;

    private final UUID chatRoomID;

    public ImageMessage(ImageMessageBuilder imageMessageBuilder){
        this.image= imageMessageBuilder.image;
        this.timeSent = imageMessageBuilder.timeSent;
        this.sender= imageMessageBuilder.sender;
        this.chatRoomID = imageMessageBuilder.chatRoomID;
    }

    public static class ImageMessageBuilder implements Serializable{
        private BufferedImage image;
        private String timeSent;
        private User sender;
        private UUID chatRoomID;

        public ImageMessageBuilder image (BufferedImage image){
            this.image = image;
            return this;
        }

        public ImageMessageBuilder timeSent() {
            this.timeSent = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
            return this;
        }

        public ImageMessageBuilder sender(User sender) {
            this.sender = sender;
            return this;
        }

        public ImageMessageBuilder chatRoomID(UUID chatRoomID){
            this.chatRoomID = chatRoomID;
            return this;
        }

        public ImageMessage build(){
            return new ImageMessage(this);
        }
    }



    public BufferedImage getImage(){
        return image;
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
    public UUID getChatRoomID(){
        return chatRoomID;
    }

    @Override
    public void dataHandler(Object userStorage, Object chatroomStorage, ObjectOutputStream outputStream){
//        todo: hantera bilder, lägg in i chatroomhistory

    }



}
