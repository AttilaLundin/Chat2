package sharedresources.requests;

import server.userStorage;
import sharedresources.interfaces.DataHandler;
import sharedresources.interfaces.User;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class RegisterRequest implements User, DataHandler, Serializable{
    private final String username;
    private final String password;
    private final String displayname;

    public RegisterRequest(RegisterBuilder registerBuilder){
        this.username= Objects.requireNonNull(registerBuilder.username);
        this.password = Objects.requireNonNull(registerBuilder.password);
        this.displayname = Objects.requireNonNull(registerBuilder.displayName);
    }

    public static class RegisterBuilder{
        private String username;
        private String password;
        private String displayName;
        public RegisterBuilder username(String username){
            this.username = username;
            return this;
        }
        public RegisterBuilder displayName(String displayName){
            this.displayName = displayName;
            return this;
        }
        public RegisterBuilder password(String password){
            this.password = password;
            return this;
        }

        public RegisterRequest build(){
            return new RegisterRequest(this);
        }

    }

    public String getUsername(){
        return username;
    }

    @Override
    public void dataHandler(Object registeredUser, Object chatHistory, ObjectOutputStream outputStream) {
        userStorage userStorage = (userStorage) registeredUser;
        try {
            outputStream.writeObject(userStorage.createUser(this));
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public String getPassword(){
        return password;
    }
    public String getDisplayName(){
        return displayname;
    }



}
