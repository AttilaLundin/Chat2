package model;

import controller.ClientHandler;
import interfaces.User;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class SessionUser implements User, Serializable{
    private String username;
    private String displayname;
    private String password;
    private Map<String, String> LogInfo;

    private SessionUser(SessionUserBuilder SessionUserBuilder){
        this.username= Objects.requireNonNull(SessionUserBuilder.username);
        this.displayname = Objects.requireNonNull(SessionUserBuilder.displayname);
        this.password = Objects.requireNonNull(SessionUserBuilder.password);
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public void userHandler(Object registeredUsers, Object chatHistory, ObjectOutputStream outputStream) {



    }





    public void addPassword(){
        LogInfo = new HashMap<>();
        LogInfo.put(this.username, this.password);
    }


    public String getDisplayname() {
        return displayname;
    }

    public boolean correctCredentials(Login login){
        return username.equals(login.getUsername()) && password.equals(login.getPassword());
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


}
