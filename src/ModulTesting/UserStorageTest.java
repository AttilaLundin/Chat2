package ModulTesting;
import org.junit.Test;
import server.UserStorage;
import common.RegisteredUser;
import common.requests.LoginRequest;
import common.requests.RegisterRequest;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserStorageTest {
@Test
     public void testValidateUser(){
        // Write your test case for validateUser() method
        // Create an instance of UserStorage and test the validateUser() method
        UserStorage userStorage = new UserStorage();
        // Use Assertions.assertEquals() or other assertions to verify the result
        // Create a registered user for testing
        RegisteredUser user = new RegisteredUser.UserBuilder()
                .username("testUser")
                .password("testPassword")
                .build();
        // Add the user to the user storage
        userStorage.getAllUsers().put(user.getUsername(), user);
        // Create a login request with correct credentials
        LoginRequest correctCredentials = new LoginRequest.LoginBuilder().username("testUser"). password("testPassword").build();
        // Test the validateUser() method with correct credentials
        RegisteredUser result = userStorage.validateUser(correctCredentials);
         // Create a login request with non-existing username
         LoginRequest nonExistingUser = new LoginRequest.LoginBuilder().username("nonExistingUser"). password("password").build();
         // Test the validateUser() method with non-existing username
         result = userStorage.validateUser(nonExistingUser);

    }
}
