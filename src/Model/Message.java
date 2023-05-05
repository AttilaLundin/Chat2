package Model;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.UUID;

public class Message implements Serializable {
    private final String text;
    private transient BufferedImage image;
    private String time;
    private User sender;

    private final UUID chatRoomID;

    public Message(String text, BufferedImage image, User sender, UUID chatRoomID){
        this.text= text;
        this.image = image;
        time = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
        this.sender= sender;
        this.chatRoomID = chatRoomID;
    }

    public String getText(){
        return text;
    }
    public UUID getChatRoomID(){
        return chatRoomID;
    }




}
