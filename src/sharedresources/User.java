package sharedresources;

import server.ChatRoomStorage;
import server.UserStorage;
import sharedresources.interfaces.DataHandler;
import sharedresources.requests.LoginRequest;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class User implements sharedresources.interfaces.User, DataHandler, Serializable{
    private String username;

    private String password;

    private User(UserBuilder userBuilder){
        this.username= Objects.requireNonNull(userBuilder.username);
        this.password = Objects.requireNonNull(userBuilder.password);
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

    public boolean correctCredentials(LoginRequest loginRequest){
        return username.equals(loginRequest.getUsername()) && password.equals(loginRequest.getPassword());
    }

    public static class UserBuilder {

        private String username;
        private String password;

        public UserBuilder username(String username){
            this.username = username;
            return this;
        }

        public UserBuilder password(String password){
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
