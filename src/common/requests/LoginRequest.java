package common.requests;


import common.User;
import server.ChatRoomStorage;
import server.UserStorage;
import common.RegisteredUser;
import common.DataHandler;

import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * This class implements the User, DataHandler, and Serializable interfaces.
 * It represents a login request from a client, containing the client's username and password.
 */
public class LoginRequest implements User, DataHandler, Serializable {

    private final String username;
    private final String password;

    /**
     * Initializes a new LoginRequest with the given LoginBuilder.
     *
     * @param loginBuilder The builder used to build the login request.
     */
    public LoginRequest(LoginBuilder loginBuilder){
        this.username = loginBuilder.username;
        this.password = loginBuilder.password;
    }

    /**
     * This class helps in building a login request object.
     */
    public static class LoginBuilder {
        private String username;
        private String password;

        /**
         * Sets the username for the login request.
         *
         * @param username The username to set.
         * @return This LoginBuilder object.
         */
        public LoginBuilder username(String username){
            this.username = username;
            return this;
        }

        /**
         * Sets the password for the login request.
         *
         * @param password The password to set.
         * @return This LoginBuilder object.
         */
        public LoginBuilder password(String password){
            this.password = password;
            return this;
        }

        /**
         * Builds the login request object using the provided username and password.
         *
         * @return A new LoginRequest object.
         */
        public LoginRequest build(){
            return new LoginRequest(this);
        }

    }

    /**
     * Returns the username from the login request.
     *
     * @return The username of the login request.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Handles the login request by validating the user in the user storage
     * and sends the user object to the client through the output stream.
     *
     * @param userStorage The storage of users.
     * @param chatRoomStorage The storage of chat rooms.
     * @param outputStream The output stream to the client.
     */
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatRoomStorage, ObjectOutputStream outputStream) {

        try {
            RegisteredUser user = userStorage.validateUser(this);

            outputStream.writeObject(user);
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Returns the password from the login request.
     *
     * @return The password of the login request.
     */
    public String getPassword(){
        return password;
    }

}
