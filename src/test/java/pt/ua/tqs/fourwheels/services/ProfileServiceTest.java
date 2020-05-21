package pt.ua.tqs.fourwheels.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(value = ProfileServiceTest.class)
class ProfileServiceTest {
    @Autowired
    @BeforeEach
    void setUp(){
    }
    @Test
    void getbyid(){
        //assertNotEquals(null, serv.getUserById(2));
    }
}