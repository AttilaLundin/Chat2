import common.RegisteredUser;
import common.requests.LoginRequest;
import common.requests.RegisterRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.UserStorage;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the functionality of the UserStorage class, ensuring the correct
 * user validation and user creation behavior.
 * @author Shazhaib Kazmi
 */
public class UserStorageTest {

    private UserStorage userStorage;

    /**
     * Setup method that initializes a new UserStorage instance before each test.
     */
    @BeforeEach
    public void setup() {
        userStorage = new UserStorage();
    }

    /**
     * Tests whether the validateUser method works as expected by adding a user to the storage and then attempting
     * to validate them using correct and incorrect credentials.
     */
    @Test
    public void testValidateUser() {
        RegisteredUser user = new RegisteredUser.RegisteredUserBuilder()
                .username("testUser")
                .password("testPassword")
                .build();

        userStorage.getAllUsers().put(user.getUsername(), user);


        LoginRequest correctCredentials = new LoginRequest.LoginBuilder().username("testUser"). password("testPassword").build();

        RegisteredUser result = userStorage.validateUser(correctCredentials);
        assertNotNull(result);
        assertEquals(user, result);

        LoginRequest incorrectCredentials = new LoginRequest.LoginBuilder().username("testUser"). password("wrongPassword").build();

        result = userStorage.validateUser(incorrectCredentials);
        assertNull(result);
    }

    /**
     * Tests the createUser method by attempting to create a new user and then attempting to create a duplicate user.
     */
    @Test
    public void testCreateUser() {
        RegisterRequest registerRequest = new RegisterRequest.RegisterBuilder().username("newUser").password("newPassword").build();

        RegisteredUser newUser = userStorage.createUser(registerRequest);
        assertNotNull(newUser);
        assertEquals("newUser", newUser.getUsername());

        RegisterRequest duplicateUserRequest = new RegisterRequest.RegisterBuilder().username("newUser").password("otherPassword").build();
        RegisteredUser duplicateUser = userStorage.createUser(duplicateUserRequest);
        assertNull(duplicateUser);
    }
}