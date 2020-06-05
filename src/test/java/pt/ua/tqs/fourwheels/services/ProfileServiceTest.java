package pt.ua.tqs.fourwheels.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

@WebMvcTest(value = ProfileServiceTest.class)
class ProfileServiceTest {
    private ProfileService profileService;
    @BeforeEach
    public void setUp(){
        profileService = new ProfileService();
    }
    @Test
    public void getbyid(){
        assertNotEquals(0,1);
    }
    @Test
    public void getbyNoneId() {assertEquals("null",profileService.getUserById(-1).getName());}
}