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

    public User validateUser(User loginAttempt){
        User user = registeredUsers.get(loginAttempt.getUserName());
        if(user.equals(loginAttempt)){
            System.out.println("validation successful");
            return user;
        }
        else{
            System.out.println("validation unsuccessful");
            return null;
        }
    }

    public boolean createUser(Register register){
        String username = register.getUsername();
        if(registeredUsers.containsKey(username)) return false;
        registeredUsers.put(username, new User.Builder("test", "testp").build());
        return true;
    }


}
