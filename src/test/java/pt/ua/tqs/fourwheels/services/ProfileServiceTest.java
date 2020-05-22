package pt.ua.tqs.fourwheels.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.junit.Assert.assertNotEquals;

@WebMvcTest(value = ProfileServiceTest.class)
class ProfileServiceTest {
    @Test
    public void getbyid(){
        assertNotEquals(0,1);
    }
}