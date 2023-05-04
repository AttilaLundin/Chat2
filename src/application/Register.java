package application;

import java.io.Serializable;

public class Register implements Serializable {
    private final String userName;
    private final String password;
    private  String DisplayName;

    public Register(String Username, String password,String displayName){
        this.userName= Username;
        this.password = password;
        this.DisplayName = DisplayName;
    }

    public Register(){
        this.userName= "username";
        this.password = "password";
    }

    public String getUsername(){
        return userName;
    }
    public String getPassword(){
        return password;
    }

}
