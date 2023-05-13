package common;

import server.ChatRoomStorage;
import server.UserStorage;
import common.requests.LoginRequest;

import java.io.ObjectOutputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a registered user in a chat application. Each registered user is associated with
 * a unique username and password. This class implements the User and DataHandler interfaces.
 */
public class RegisteredUser implements User, DataHandler, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String username;

    private final String password;

    /**
     * Constructs a RegisteredUser with the specified builder.
     *
     * @param userBuilder the builder to construct a registered user
     */
    private RegisteredUser(UserBuilder userBuilder) {
        this.username = Objects.requireNonNull(userBuilder.username);
        this.password = Objects.requireNonNull(userBuilder.password);
    }

    /**
     * Returns the username of the registered user.
     *
     * @return the username
     */
    @Override
    public String getUsername() {
        return username;
    }

    /**
     * Handles the data for the registered user by writing all associated chat rooms to an ObjectOutputStream.
     *
     * @param userStorage     the user storage
     * @param chatRoomStorage the chat room storage
     * @param outputStream    the output stream to write the data to
     */
    @Override
    public void dataHandler(UserStorage userStorage, ChatRoomStorage chatRoomStorage, ObjectOutputStream outputStream) {

        try {
            outputStream.writeObject(chatRoomStorage.getAllChatRooms(this));
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks whether the specified login request has the same username and password as the registered user.
     *
     * @param loginRequest the login request
     * @return true if the credentials match, false otherwise
     */
    public boolean correctCredentials(LoginRequest loginRequest) {
        return username.equals(loginRequest.getUsername()) && password.equals(loginRequest.getPassword());
    }

    /**
     * This class provides a way to build a RegisteredUser.
     */
    public static class UserBuilder {

        private String username;
        private String password;

        /**
         * Sets the username for the RegisteredUser to be built.
         *
         * @param username the username
         * @return this builder
         */
        public UserBuilder username(String username) {
            this.username = username;
            return this;
        }

        /**
         * Sets the password for the RegisteredUser to be built.
         *
         * @param password the password
         * @return this builder
         */
        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Returns a new RegisteredUser built from this builder.
         *
         * @return a new RegisteredUser
         */
        public RegisteredUser build() {
            return new RegisteredUser(this);
        }

    }

    /**
     * Checks if the specified object equals this RegisteredUser.
     * The result is true if and only if the argument is a RegisteredUser object with the same username.
     *
     * @param other the object to compare with
     * @return true if the objects are the same; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }
        RegisteredUser user = (RegisteredUser) other;
        return this.username.equals(user.username);
    }

    /**
     * Returns a hash code value for this RegisteredUser.
     * The hash code is based on the username of the RegisteredUser.
     *
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        return this.username.hashCode();
    }
}