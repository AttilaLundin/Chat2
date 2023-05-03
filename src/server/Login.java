package server;

import application.User;

public class Login {

    public boolean LoginAuthenticator(User user){
        String PasswordStored = user.getPasswordFromServer(user.getUserName());
        return user.getPassword().equals(PasswordStored);
    }
}///r
