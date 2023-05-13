package common;

import server.ChatRoomStorage;
import server.UserStorage;
import common.requests.LoginRequest;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class RegisteredUser implements User, DataHandler, Serializable{
    private final String username;

    private final String password;

    private RegisteredUser(UserBuilder userBuilder){
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

        public RegisteredUser build(){
            return new RegisteredUser(this);
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
        RegisteredUser user = (RegisteredUser) other;
        return this.username.equals((user.username));
    }

    @Override
    public int hashCode(){
        return this.username.hashCode();
    }


}
