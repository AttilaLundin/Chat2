package server;

import application.Register;
import application.User;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class RegisteredUsers {

    private final Map<String, User> registeredUsers;

    public RegisteredUsers(){
        registeredUsers = Collections.synchronizedMap(new HashMap<>());

    }

    public boolean validateUser(User loginAttempt){
        User user = registeredUsers.get(loginAttempt.getUserName());
        if(user == null) return false;
        return user.equals(loginAttempt);
    }

    public boolean createUser(Register register){
        String username = register.getUsername();
        if(registeredUsers.containsKey(username)) return false;
        registeredUsers.put(username, new User(username, register.getPassword(), username));
        return true;
    }
}
