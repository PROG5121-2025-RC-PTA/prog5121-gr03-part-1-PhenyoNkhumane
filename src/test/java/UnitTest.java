import org.example.Login;
import org.example.Register;
import org.example.User;
import org.example.UserStorage;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class UnitTest {

    @BeforeEach
    public void setup() {
        UserStorage.initialize(); // Reset before each test
    }

    @Test
    public void testRegisterUser_Success() {
        boolean result = Register.registerUser("testuser", "testpass", "Test", "User", "1234567890");
        assertTrue(result, "User should be registered successfully");
    }

    @Test
    public void testRegisterUser_AlreadyExists() {
        Register.registerUser("testuser", "testpass", "Test", "User", "1234567890");
        boolean result = Register.registerUser("testuser", "testpass", "Test", "User", "1234567890");
        assertFalse(result, "User registration should fail for duplicate username");
    }

    @Test
    public void testLoginUser_Success() {
        Register.registerUser("loginuser", "mypassword", "Login", "User", "0987654321");
        User user = Login.loginUser("loginuser", "mypassword");
        assertNotNull(user, "Login should succeed with correct credentials");
        assertEquals("loginuser", user.getUsername());
    }

    @Test
    public void testLoginUser_FailWrongPassword() {
        Register.registerUser("failuser", "rightpass", "Fail", "User", "0000000000");
        User user = Login.loginUser("failuser", "wrongpass");
        assertNull(user, "Login should fail with wrong password");
    }

    @Test
    public void testLoginUser_NonexistentUser() {
        User user = Login.loginUser("ghost", "password");
        assertNull(user, "Login should fail for nonexistent user");
    }
}
