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
        registeredUsers.put("test", new User.Builder("test", "testp").build());
    }

    public boolean validateUser(User loginAttempt){
        User user = registeredUsers.get(loginAttempt.getUserName());
        if(user == null){
            System.out.println("validation: false");
            return false;
        }
        boolean bool = user.equals(loginAttempt);
        System.out.println("validation " + bool);
        return bool;
    }

    public boolean createUser(Register register){
        String username = register.getUsername();
        if(registeredUsers.containsKey(username)) return false;
        registeredUsers.put(username, new User.Builder("test", "testp").build());
        return true;
    }
}
