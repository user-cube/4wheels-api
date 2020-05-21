package pt.ua.tqs.fourwheels.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import pt.ua.tqs.fourwheels.entities.Profile;

import static org.junit.Assert.*;

@WebMvcTest(value = ProfileServiceTest.class)
class ProfileServiceTest {
    @Autowired
    private ProfileService serv;
    @BeforeEach
    void setUp(){
        Profile pr = new Profile();
        pr.setId(2);
        serv.addUser(pr);
    }
    @Test
    void getbyid(){
        assertNotEquals(null, serv.getUserById(2));
    }
}