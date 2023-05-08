package sharedresources;

import server.chatRoomStorage;
import sharedresources.interfaces.DataHandler;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class User implements sharedresources.interfaces.User, DataHandler, Serializable{
    private String username;
    private String displayname;
    private String password;

    private User(SessionUserBuilder sessionUserBuilder){
        this.username= Objects.requireNonNull(sessionUserBuilder.username);
        this.displayname = Objects.requireNonNull(sessionUserBuilder.displayname);
        this.password = Objects.requireNonNull(sessionUserBuilder.password);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void dataHandler(Object registeredUsers, Object history, ObjectOutputStream outputStream) {

        chatRoomStorage chatroomStorage = (chatRoomStorage) history;
        try {
            outputStream.writeObject(chatroomStorage.getChatRooms(this));
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getDisplayName() {
        return displayname;
    }

    public boolean correctCredentials(LoginRequest loginRequest){
        return username.equals(loginRequest.getUsername()) && password.equals(loginRequest.getPassword());
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

        public User build(){
            return new User(this);
        }

    }


}
