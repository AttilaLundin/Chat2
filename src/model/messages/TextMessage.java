package model.messages;

import model.messages.Message;
import model.user.SessionUser;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class TextMessage implements Message,Serializable {
    private final String text;
    private String timeSent;
    private SessionUser sender;

    private UUID chatRoomID;

    public TextMessage(String text, BufferedImage image, SessionUser sender, UUID chatRoomID){
        this.text= text;
        timeSent = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.sender= sender;
        this.chatRoomID = chatRoomID;
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

    public void messageHandler(){

    }




}
