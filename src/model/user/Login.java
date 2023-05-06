package model.user;

import java.io.Serializable;
import java.util.Objects;

public class Login implements User, Serializable {

    private final String username;
    private final String password;
    private  String displayname;

    public Login(String username, String password,String displayName){
        this.username = Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.displayname = Objects.requireNonNull(displayName);
    }


    public String getUsername(){
        return username;
    }

    @Override
    public void userHandler() {

    }

    public String getPassword(){
        return password;
    }


    public boolean RegisterUser(){
        return true;
    }
}
