package server;

import sharedresources.User;
import sharedresources.requests.LoginRequest;
import sharedresources.requests.RegisterRequest;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStorage implements Serializable {

    private final Map<String, User> registeredUsers;

    public UserStorage(){
        registeredUsers = new ConcurrentHashMap<>();
          //todo: remove
//        registeredUsers.put("test", new User.UserBuilder().username("test").password("test").build());
//        registeredUsers.put("test1", new User.UserBuilder().username("test1").password("test1").build());
//        registeredUsers.put("test2", new User.UserBuilder().username("test2").password("test2").build());
//        registeredUsers.put("test3", new User.UserBuilder().username("test3").password("test3").build());
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
        User newUser = new User.UserBuilder().username(registerRequest.getUsername()).password(registerRequest.getPassword()).build();
        registeredUsers.put(username, newUser);
        return newUser;
    }

    public Map<String, User> getAllUsers(){
        return registeredUsers;
    }
}
