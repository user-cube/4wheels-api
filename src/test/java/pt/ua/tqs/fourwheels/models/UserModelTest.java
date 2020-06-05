package pt.ua.tqs.fourwheels.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.assertEquals;

@WebMvcTest(value = UserModelTest.class)
public class UserModelTest {

    private UserModel userModel;
    @BeforeEach
    public void setUp(){
        userModel = new UserModel();
    }

    @Test
    public void unitTestsUserModel(){
        userModel.setUsername("admin");
        userModel.setPassword("admin");
        assertEquals("admin", userModel.getUsername());
        assertEquals("admin", userModel.getPassword());
    }

    @Test
    public void unitTestsUserModelToString(){
        userModel.setUsername("admin");
        userModel.setPassword("admin");
        UserModel um = new UserModel("admin","admin");
        assertEquals(userModel.toString(), um.toString());
    }
}



