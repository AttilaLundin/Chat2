package sharedresources;

import server.RegisteredUsers;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

public class Register implements User, Serializable{
    private final String username;
    private final String password;
    private final String displayname;

    public Register(RegisterBuilder registerBuilder){
        this.username= Objects.requireNonNull(registerBuilder.username);
        this.password = Objects.requireNonNull(registerBuilder.password);
        this.displayname = Objects.requireNonNull(registerBuilder.displayname);
    }

    public static class RegisterBuilder{
        private String username;
        private String password;
        private String displayname;
        public RegisterBuilder username(String username){
            this.username = username;
            return this;
        }
        public RegisterBuilder displayname(String displayname){
            this.displayname = displayname;
            return this;
        }
        public RegisterBuilder password(String password){
            this.password = password;
            return this;
        }

        public Register build(){
            return new Register(this);
        }

    }

    public String getUsername(){
        return username;
    }

    @Override
    public void userHandler(Object registeredUser, Object chatHistory, ObjectOutputStream outputStream) {
        RegisteredUsers registeredUsers = (RegisteredUsers) registeredUser;
        try {
            outputStream.writeObject(registeredUsers.createUser(this));
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
