package application;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private int UsernamePasswordPair;
    private String UserName;
    private String displayName;
    private String password;
    private Map<String, String> LogInfo;

    private List<UUID> chatRoomIDs;

    public User(String Username, String displayName, String password, List<UUID> chatRoomIDs){
        this.UserName= Username;
        this.displayName = displayName;
        this.password = password;
        this.chatRoomIDs = chatRoomIDs;
    }
    public User(String Username, String displayName, String password){
        this.UserName= Username;
        this.displayName = displayName;
        this.password = password;
        this.chatRoomIDs = new ArrayList<>();
    }

    public User(){
        this.UserName= "username";
        this.displayName = "displayName";
        this.password = "password";
    }
    public void addPassword(){
        LogInfo = new HashMap<>();
        LogInfo.put(this.UserName, this.password);
    }

    public boolean validPassword(User user){
        return this.password.equals(user.password);
    }
    public String getPasswordFromServer(String UserName){
       return LogInfo.get(UserName);
    }
    public String getPassword(){
        return password;
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
