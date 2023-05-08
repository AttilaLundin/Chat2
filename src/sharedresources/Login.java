package sharedresources;


import server.userStorage;

import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Login implements User,DataHandler, Serializable {

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
    public void dataHandler(Object registeredUser, Object chatHistory, ObjectOutputStream outputStream) {
        userStorage userStorage = (userStorage)registeredUser;
        try {
            SessionUser sessionUser = userStorage.validateUser(this);

            outputStream.writeObject(sessionUser);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getPassword(){
        return password;
    }

}
