package sharedresources;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.interfaces.DataHandler;
import sharedresources.requests.LoginRequest;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import java.util.List;

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
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatRoomStorage, ObjectOutputStream outputStream) {

        try {
            outputStream.writeObject(chatRoomStorage.getAllChatRooms(this));
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

        public User build(){
            return new User(this);
        }

    }

    @Override
    public boolean equals(Object other){
        if(other == this) {
            return true;
        }
        if(other == null || other.getClass() != this.getClass()) {
            return false;
        }
        User user = (User) other;
        return this.username.equals((user.username));
    }

    @Override
    public int hashCode(){
        return this.username.hashCode();
    }


}
