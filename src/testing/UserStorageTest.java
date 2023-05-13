package server;

import common.RegisteredUser;
import common.requests.LoginRequest;
import common.requests.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserStorageTest {

    UserStorage userStorage;

    @BeforeEach
    public void setup() {
        userStorage = new UserStorage();
    }

    @Test
    public void testValidateUser() {
        // Create a registered user for testing
        RegisteredUser user = new RegisteredUser.RegisteredUserBuilder()
                .username("testUser")
                .password("testPassword")
                .build();

        // Add the user to the user storage
        userStorage.getAllUsers().put(user.getUsername(), user);

        // Create a login request with correct credentials
        LoginRequest correctCredentials = new LoginRequest.LoginBuilder().username("testUser"). password("testPassword").build();

        // Test the validateUser() method with correct credentials
        RegisteredUser result = userStorage.validateUser(correctCredentials);
        assertNotNull(result);
        assertEquals(user, result);

        // Create a login request with incorrect credentials
        LoginRequest incorrectCredentials = new LoginRequest.LoginBuilder().username("testUser"). password("wrongPassword").build();

        // Test the validateUser() method with incorrect credentials
        result = userStorage.validateUser(incorrectCredentials);
        assertNull(result);
    }

    @Test
    public void testCreateUser() {
        // Create a register request
        RegisterRequest registerRequest = new RegisterRequest.RegisterBuilder().username("newUser").password("newPassword").build();

        // Test the createUser() method
        RegisteredUser newUser = userStorage.createUser(registerRequest);
        assertNotNull(newUser);
        assertEquals("newUser", newUser.getUsername());

        // Try to create a user with the same username
        RegisterRequest duplicateUserRequest = new RegisterRequest.RegisterBuilder().username("newUser").password("otherPassword").build();
        RegisteredUser duplicateUser = userStorage.createUser(duplicateUserRequest);
        assertNull(duplicateUser);
    }
}
