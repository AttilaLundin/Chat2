package server;

import sharedresources.User;
import sharedresources.requests.LoginRequest;
import sharedresources.requests.RegisterRequest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserStorage {

    private final Map<String, User> registeredUsers;

    public UserStorage(){
        registeredUsers = Collections.synchronizedMap(new HashMap<>());
//        TODO: remove
        registeredUsers.put("test", new User.SessionUserBuilder().username("test").password("test").displayname("test").build());
        registeredUsers.put("test1", new User.SessionUserBuilder().username("test1").password("test1").displayname("test1").build());
        registeredUsers.put("test2", new User.SessionUserBuilder().username("test2").password("test2").displayname("test2").build());
        registeredUsers.put("test3", new User.SessionUserBuilder().username("test3").password("test3").displayname("test3").build());
    }

    public User validateUser(LoginRequest loginRequestAttempt){
        User user = registeredUsers.get(loginRequestAttempt.getUsername());
        Set<String> keys = registeredUsers.keySet();

        for(String s : keys){
            System.out.println(s);
        }

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

    public Map<String, User> getAllUsers(){
        return registeredUsers;
    }
}
