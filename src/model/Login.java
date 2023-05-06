package model;

import controller.ClientHandler;
import controller.Server;
import interfaces.User;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Login implements User, Serializable {

    private final String username;
    private final String password;

    public Login(LoginBuilder loginBuilder){
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

        public Login build(){
            return new Login(this);
        }

    }

    public String getUsername(){
        return username;
    }

    @Override
    public void userHandler(Object registeredUser, Object chatHistory, ObjectOutputStream outputStream) {
        RegisteredUsers registeredUsers = (RegisteredUsers)registeredUser;
        try {
            outputStream.writeObject(registeredUsers.validateUser(this));
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getPassword(){
        return password;
    }

}
