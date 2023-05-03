package application;

import java.io.Serializable;
import java.util.*;
public class User implements Serializable {
    private String UserName;
    private String displayName;
    private String PassWord;

    private List<UUID> chatRoomIDs;

    public User(String Username, String displayName, String PassWord, List<UUID> chatRoomIDs){
        this.UserName= Username;
        this.displayName = displayName;
        this.PassWord = PassWord;
        this.chatRoomIDs = chatRoomIDs;
    }

    public User(){
        this.UserName= "Username";
        this.displayName = "displayName";
        this.PassWord = "PAssWord"; // varför stort W 🤨 jag vet inte bara blir så iblöand eller hru???

    }

    public void addChatRoom(UUID chatRoomID){
        this.chatRoomIDs.add(chatRoomID);
    }

    public List<UUID> getChatRoomIDs(){return chatRoomIDs;}

    public String getUserName(){return UserName;}

    public String getDisplayName() {
        return displayName;
    }
}
