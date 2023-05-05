package Model;

import java.io.Serializable;
import java.util.*;

public class User implements Serializable {
    private String username;
    private String displayName;
    private String password;
    private Map<String, String> LogInfo;
    private List<UUID> chatRoomIDs;

    private User(Builder builder){
        this.username= builder.username;
        this.displayName = builder.displayName;
        this.password = builder.password;
        this.chatRoomIDs = builder.chatRoomIDs;
    }

    public static class Builder{
        private String username;
        private String displayName;
        private String password;
        private List<UUID> chatRoomIDs;

        public Builder (String username, String password){
            this.username = username;
            this.password = password;
        }

        public Builder displayName (String displayName){
            this.displayName = displayName;
            return this;
        }

        public Builder chatRoomIDs(List<UUID> chatRoomIDs) {
            this.chatRoomIDs = chatRoomIDs;
            return this;
        }

        public User build(){
            return new User(this);
        }

    }
    public void addPassword(){
        LogInfo = new HashMap<>();
        LogInfo.put(this.username, this.password);
    }

    public void addChatRoom(UUID chatRoomID){
        this.chatRoomIDs.add(chatRoomID);
    }

    public List<UUID> getChatRoomIDs(){return chatRoomIDs;}

    public String getUserName(){return username;}

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;

        return user.username.equals(this.username) && user.password.equals(this.password);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.username);
    }

}
