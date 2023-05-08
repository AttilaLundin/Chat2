package server;

import sharedresources.SessionUser;
import sharedresources.LoginRequest;
import sharedresources.RegisterRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class userStorage {

    private final Map<String, SessionUser> registeredUsers;

    public userStorage(){
        registeredUsers = Collections.synchronizedMap(new HashMap<>());
        registeredUsers.put("test", new SessionUser.SessionUserBuilder().username("test").password("test").displayname("test").build());
        registeredUsers.put("test1", new SessionUser.SessionUserBuilder().username("test1").password("test1").displayname("test1").build());
    }

    public SessionUser validateUser(LoginRequest loginRequestAttempt){
        SessionUser user = registeredUsers.get(loginRequestAttempt.getUsername());
        if(user == null) return null;
        if (user.correctCredentials(loginRequestAttempt)) {
            return user;
        }
        else{
            return null;
        }
    }

    public SessionUser createUser(RegisterRequest registerRequest){
        String username = registerRequest.getUsername();
        if(registeredUsers.containsKey(username)) return null;
        SessionUser newUser = new SessionUser.SessionUserBuilder().username(registerRequest.getUsername()).password(registerRequest.getPassword()).displayname(registerRequest.getDisplayName()).build();
        registeredUsers.put(username, newUser);
        return newUser;
    }


}
