package sharedresources;


import server.userStorage;
import sharedresources.interfaces.DataHandler;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class LoginRequest implements sharedresources.interfaces.User, DataHandler, Serializable {

    private final String username;
    private final String password;

    public LoginRequest(LoginBuilder loginBuilder){
        this.username = loginBuilder.username;
        this.password = loginBuilder.password;
    }

    public static class LoginBuilder {
        private String username;
        private String password;

        public LoginBuilder username(String username){
            this.username = username;
            return this;
        }


        public LoginBuilder password(String password){
            this.password = password;
            return this;
        }

        public LoginRequest build(){
            return new LoginRequest(this);
        }

    }

    public String getUsername(){
        return username;
    }

    @Override
    public void dataHandler(Object registeredUser, Object chatHistory, ObjectOutputStream outputStream) {
        userStorage userStorage = (userStorage)registeredUser;
        try {
            User user = userStorage.validateUser(this);

            outputStream.writeObject(user);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getPassword(){
        return password;
    }

}
