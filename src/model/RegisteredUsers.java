package model;

import model.user.Register;
import model.user.SessionUser;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class RegisteredUsers {

    private final Map<String, SessionUser> registeredUsers;

    public RegisteredUsers(){
        registeredUsers = Collections.synchronizedMap(new HashMap<>());
        registeredUsers.put("test", new SessionUser.Builder("test", "testp").build());
    }

    public SessionUser validateUser(SessionUser loginAttempt){
        SessionUser user = registeredUsers.get(loginAttempt.getUsername());
        if(user == null) return null;
        if (user.equals(loginAttempt)) {
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
        registeredUsers.put(username, new SessionUser.Builder("test", "testp").build());
        return true;
    }


}
