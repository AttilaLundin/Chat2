package model.user;

import java.io.Serializable;
import java.util.*;

public class SessionUser implements User, Serializable{
    private String username;
    private String displayname;
    private String password;
    private Map<String, String> LogInfo;
    private List<UUID> chatRoomIDs;

    private SessionUser(Builder builder){
        this.username= builder.username;
        this.displayname = builder.displayname;
        this.password = builder.password;
        this.chatRoomIDs = builder.chatRoomIDs;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void userHandler() {

    }
    public static class Builder{
        private String username;
        private String displayname;
        private String password;
        private List<UUID> chatRoomIDs;

        public Builder (String username, String password){
            this.username = username;
            this.password = password;
        }

        public Builder displayName (String displayName){
            this.displayname = displayName;
            return this;
        }

        public Builder chatRoomIDs(List<UUID> chatRoomIDs) {
            this.chatRoomIDs = chatRoomIDs;
            return this;
        }

        public SessionUser build(){
            return new SessionUser(this);
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

    public String getDisplayname() {
        return displayname;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        SessionUser user = (SessionUser) obj;

        return user.username.equals(this.username) && user.password.equals(this.password);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.username);
    }

}
