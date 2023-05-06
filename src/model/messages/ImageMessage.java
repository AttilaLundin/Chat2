package model.messages;

import model.user.SessionUser;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class ImageMessage implements Message,Serializable {
    private final transient BufferedImage image;
    private final String timeSent;
    private final SessionUser sender;

    private final UUID chatRoomID;

    public ImageMessage(String text, BufferedImage image, SessionUser sender, UUID chatRoomID){
        this.image = image;
        timeSent = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.sender= sender;
        this.chatRoomID = chatRoomID;
    }

    public BufferedImage getImage(){
        return image;
    }

    @Override
    public String getTimeSent() {
        return timeSent;
    }

    @Override
    public SessionUser getSender() {
        return sender;
    }

    @Override
    public UUID getChatRoomID(){
        return chatRoomID;
    }

    @Override
    public void messageHandler(){

    }



}
