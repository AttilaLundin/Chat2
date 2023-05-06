package model;

import interfaces.User;

import java.io.Serializable;
import java.util.*;

public class SessionUser implements User, Serializable{
    private String username;
    private String displayname;
    private String password;
    private Map<String, String> LogInfo;
    private List<UUID> chatRoomIDs;

    private SessionUser(SessionUserBuilder SessionUserBuilder){
        this.username= Objects.requireNonNull(SessionUserBuilder.username);
        this.displayname = Objects.requireNonNull(SessionUserBuilder.displayname);
        this.password = Objects.requireNonNull(SessionUserBuilder.password);
        this.chatRoomIDs = SessionUserBuilder.chatRoomIDs;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void userHandler() {

    }
    public static class SessionUserBuilder{
        private String username;
        private String displayname;
        private String password;
        private List<UUID> chatRoomIDs;

        public SessionUserBuilder username(String username){
            this.username = username;
            return this;
        }
        public SessionUserBuilder displayname(String displayname){
            this.displayname = displayname;
            return this;
        }
        public SessionUserBuilder password(String password){
            this.password = password;
            return this;
        }


        public SessionUserBuilder chatRoomIDs(List<UUID> chatRoomIDs) {
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
