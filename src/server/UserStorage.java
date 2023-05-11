package server;

import sharedresources.User;
import sharedresources.requests.LoginRequest;
import sharedresources.requests.RegisterRequest;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class that represents the storage of registered users in the server of a chat application.
 * This class is responsible for user validation and user creation.
 */
public class UserStorage implements Serializable {

    private final Map<String, User> registeredUsers;

    /**
     * Constructor for the UserStorage class. Initializes a ConcurrentHashMap for registered users.
     */
    public UserStorage(){
        registeredUsers = new ConcurrentHashMap<>();
    }

    /**
     * Validates a user's login credentials. If the user exists and the provided credentials match,
     * returns the User object. If the user does not exist or the credentials do not match, returns null.
     *
     * @param loginRequestAttempt the login request containing the username and password
     * @return the User object if validation is successful, null otherwise
     */
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

    /**
     * Creates a new user with the provided username and password. If a user with the same username
     * already exists, returns null.
     *
     * @param registerRequest the register request containing the username and password
     * @return the newly created User object if creation is successful, null otherwise
     */
    public User createUser(RegisterRequest registerRequest){
        String username = registerRequest.getUsername();
        if(registeredUsers.containsKey(username)) return null;
        User newUser = new User.UserBuilder().username(registerRequest.getUsername()).password(registerRequest.getPassword()).build();
        registeredUsers.put(username, newUser);
        return newUser;
    }

    /**
     * Returns a map of all registered users.
     *
     * @return a map of all registered users
     */
    public Map<String, User> getAllUsers(){
        return registeredUsers;
    }
}
