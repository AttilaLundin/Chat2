package server;

import sharedresources.SessionUser;
import sharedresources.Login;
import sharedresources.Register;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class RegisteredUsers {

    private final Map<String, SessionUser> registeredUsers;

    public RegisteredUsers(){
        registeredUsers = Collections.synchronizedMap(new HashMap<>());
        registeredUsers.put("test", new SessionUser.SessionUserBuilder().username("test").password("test").displayname("test").build());
    }

    public SessionUser validateUser(Login loginAttempt){
        SessionUser user = registeredUsers.get(loginAttempt.getUsername());
        if(user == null) return null;
        if (user.correctCredentials(loginAttempt)) {
            return user;
        }
        else{
            return null;
        }
    }

    public SessionUser createUser(Register register){
        String username = register.getUsername();
        if(registeredUsers.containsKey(username)) return null;
        SessionUser sessionUser = new SessionUser.SessionUserBuilder().username(register.getUsername()).password(register.getPassword()).displayname(register.getDisplayName()).build();
        registeredUsers.put(username, sessionUser);
        return sessionUser;
    }


}
