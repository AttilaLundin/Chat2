package common.requests;

import common.User;
import server.ChatRoomStorage;
import server.UserStorage;
import common.DataHandler;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class implements the User, DataHandler, and Serializable interfaces.
 * It represents a registration request from a client, containing the client's desired username and password.
 */
public class RegisterRequest implements User, DataHandler, Serializable{
    private final String username;
    private final String password;

    /**
     * Initializes a new RegisterRequest with the given RegisterBuilder.
     *
     * @param registerBuilder The builder used to build the registration request.
     */
    public RegisterRequest(RegisterBuilder registerBuilder){
        this.username= Objects.requireNonNull(registerBuilder.username);
        this.password = Objects.requireNonNull(registerBuilder.password);
    }

    /**
     * This class helps in building a RegisterRequest object.
     */
    public static class RegisterBuilder{
        private String username;
        private String password;

        /**
         * Sets the username for the registration request.
         *
         * @param username The username to set.
         * @return This RegisterBuilder object.
         */
        public RegisterBuilder username(String username){
            this.username = username;
            return this;
        }

        /**
         * Sets the password for the registration request.
         *
         * @param password The password to set.
         * @return This RegisterBuilder object.
         */
        public RegisterBuilder password(String password){
            this.password = password;
            return this;
        }

        /**
         * Builds the registration request object using the provided username and password.
         *
         * @return A new RegisterRequest object.
         */
        public RegisterRequest build(){
            return new RegisterRequest(this);
        }

    }

    /**
     * Returns the username from the registration request.
     *
     * @return The username of the registration request.
     */
    public String getUsername(){
        return username;
    }

    /**
     * Handles the registration request by creating a new user in the user storage
     * and sends the user object to the client through the output stream.
     *
     * @param userStorage The storage of users.
     * @param chatRoomStorage The storage of chat rooms.
     * @param outputStream The output stream to the client.
     */
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatRoomStorage, ObjectOutputStream outputStream) {
        try {
            outputStream.writeObject(userStorage.createUser(this));
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Returns the password from the registration request.
     *
     * @return The password of the registration request.
     */
    public String getPassword(){
        return password;
    }

}
