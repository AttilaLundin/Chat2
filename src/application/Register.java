package application;

import java.io.Serializable;

public class Register implements Serializable {
    private String userName;
    private String displayName;
    private String password;

    public Register(String Username, String displayName, String password){
        this.userName= Username;
        this.displayName = displayName;
        this.password = password;

    }

    public Register(){
        this.userName= "username";
        this.displayName = "displayName";
        this.password = "password";
    }

    public String getUsername(){
        return userName;
    }
    public String getPassword(){
        return password;
    }  public String getDisplayName(){
        return displayName;
    }


}
