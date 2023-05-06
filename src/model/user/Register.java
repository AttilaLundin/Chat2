package model.user;

import model.user.User;

import java.io.Serializable;
import java.util.Objects;

public class Register implements User, UserBuilder, Serializable{
    private final String username;
    private final String password;
    private final String displayname;

    public Register(String username, String password,String displayName){
        this.username= Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.displayname = Objects.requireNonNull(displayName);
    }

    public String getUsername(){
        return username;
    }
    public String getPassword(){
        return password;
    }
    public String getDisplayName(){
        return displayname;
    }

    @Override
    public void userHandler() {

    }

    @Override
    public Object build() {
        return null;
    }
}
