package application;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
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
    public User(String username, String displayName, String password){
        this.UserName= username;
        this.displayName = username;
        this.password = password;
        this.chatRoomIDs = new ArrayList<>();
    }
    public User(String username, String password){
        this.UserName= username;
        this.displayName = username;
        this.password = password;
        this.chatRoomIDs = new ArrayList<>();
    }

    public User(){
        this.UserName= "a";
        this.displayName = "a";
        this.password = "aa";
    }
    public void addPassword(){
        LogInfo = new HashMap<>();
        LogInfo.put(this.UserName, this.password);
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

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;

        return user.UserName.equals(this.UserName) && user.password.equals(this.password);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.UserName);
    }

}
