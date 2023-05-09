package server;

import sharedresources.User;
import sharedresources.requests.LoginRequest;
import sharedresources.requests.RegisterRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class UserStorage {

    private final Map<String, User> registeredUsers;

    public UserStorage(){
        registeredUsers = Collections.synchronizedMap(new HashMap<>());
        registeredUsers.put("test", new User.SessionUserBuilder().username("test").password("test").displayname("test").build());
    }

    public User validateUser(LoginRequest loginRequestAttempt){
        User user = registeredUsers.get(loginRequestAttempt.getUsername());
        if(user == null) return null;
        if (user.correctCredentials(loginRequestAttempt)) {
            return user;
        }
        else{
            return null;
        }
    }

    public User createUser(RegisterRequest registerRequest){
        String username = registerRequest.getUsername();
        if(registeredUsers.containsKey(username)) return null;
        User newUser = new User.SessionUserBuilder().username(registerRequest.getUsername()).password(registerRequest.getPassword()).displayname(registerRequest.getDisplayName()).build();
        registeredUsers.put(username, newUser);
        return newUser;
    }


}
