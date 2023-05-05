package model;

import java.io.Serializable;
import java.util.Objects;

public class Register implements Serializable {
    private final String userName;
    private final String password;
    private  String DisplayName;

    public Register(String username, String password,String displayName){
        this.userName= Objects.requireNonNull(username);
        this.password = Objects.requireNonNull(password);
        this.DisplayName = Objects.requireNonNull(displayName);
    }


    public String getUsername(){
        return userName;
    }
    public String getPassword(){
        return password;
    }


    public boolean RegisterUser(){
        return true;
    }
}
