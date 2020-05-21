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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;
import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mck;

    @MockBean
    private ProfileRepository profileRepository;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    private Profile profile;

    @BeforeEach
    public void setUp() {
        mck = MockMvcBuilders.standaloneSetup(new ProfileController(profileRepository, jwtTokenUtil))
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    @Test
    public void getInform() throws Exception {
        profile = new Profile(1, "sdfs", "ruicoelho@ua.pt", 910000000, "ewefwe", "3810", "aveiro", 211111111);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        mck.perform(get("/profile/" + 1)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("id")))
                .andExpect(jsonPath("$", hasKey("name")))
                .andExpect(jsonPath("$", hasKey("mail")));
    }
}