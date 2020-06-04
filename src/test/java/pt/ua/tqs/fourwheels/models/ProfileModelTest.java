package pt.ua.tqs.fourwheels.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.assertEquals;

import pt.ua.tqs.fourwheels.entities.Profile;

@WebMvcTest(value = ProfileModelTest.class)
public class ProfileModelTest {

    private ProfileModel profileModel;

    @BeforeEach
    public void setUp(){
        profileModel = new ProfileModel();
    }

    @Test
    public void unitTestsProfileModel(){
        Profile p = new Profile(1, "name", "mail", 1, "address", "zipCode", "city", 1, "photo");
        profileModel.setProfile(p);
        assertEquals(p, profileModel.getProfile());
    }

    @Test
    public void unitTestsProfileModelToString(){
        Profile p = new Profile(1, "name", "mail", 1, "address", "zipCode", "city", 1, "photo");
        ProfileModel pm = new ProfileModel(p);
        profileModel.setProfile(p);

        assertEquals(profileModel.toString(), pm.toString());
    }

}



