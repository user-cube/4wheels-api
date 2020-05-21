package pt.ua.tqs.fourwheels.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;
import pt.ua.tqs.fourwheels.services.ProfileService;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = ProfileController.class)
class ProfileControllerTest {

    private static Profile p1;
    @Autowired
    private MockMvc mck;

    @MockBean
    private ProfileService profileService;

    @MockBean
    private ProfileRepository profileRepository;

    @Before
    public void setUp() {
        mck = MockMvcBuilders.standaloneSetup(new ProfileController())
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    @BeforeAll
    public static void init() {
        p1 = new Profile();
        p1.setId(1);
        p1.setName("Hello");
    }

    @Test
    public void getInform() throws Exception {
        mck.perform(get("/profile/" + 1)).andDo(print());

    }

/*
    @Autowired
    private ProfileController controller;

    @Test
    void getExtraInfo(){
        System.out.println(controller.getInfo(1));
    }
*/
}