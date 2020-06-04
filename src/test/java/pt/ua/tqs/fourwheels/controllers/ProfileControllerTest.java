package pt.ua.tqs.fourwheels.controllers;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.Authentication;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;

import static org.hamcrest.Matchers.hasKey;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.assertEquals;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = ProfileController.class)
class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileRepository profileRepository;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    @MockBean
    private Authentication authentication;
    String accessToken = System.getenv("TEST_TOKEN");
    String email = System.getenv("TESTER_EMAIL");

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ProfileController(profileRepository, jwtTokenUtil, authentication))
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    @Test
    public void getInfoWithTokenOk() throws Exception {
        // Mocks
        Profile profile = new Profile(1,1, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, null);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(get("/profile/").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("id")))
                .andExpect(jsonPath("$", hasKey("name")))
                .andExpect(jsonPath("$", hasKey("mail")));

        MvcResult mock = mockMvc.perform(get("/profile/").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());

    }

    @Test
    public void getInfoWithoutToken() throws Exception{
        mockMvc.perform(get("/profile/")).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/profile/"))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    @Test
    public void putInfoWithTokenOk() throws Exception {

        // Mocks
        Profile profile = new Profile(1,1, "sdfs", "email", 910000000, "ewefwe", "3810", "aveiro", 211111111, "photo");
        JSONObject json = new JSONObject();
        json.put("id", profile.getId());
        json.put("type", profile.getType());
        json.put("name", profile.getName());
        json.put("mail", profile.getMail());
        json.put("contact", profile.getContact());
        json.put("address", profile.getAddress());
        json.put("zipCode", profile.getZipCode());
        json.put("city", profile.getCity());
        json.put("nif", profile.getNif());
        json.put("photo", profile.getPhoto());
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(put("/profile/")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json)))
                .andDo(print())
                .andExpect((status().is(200)));

        MvcResult mock = mockMvc.perform(put("/profile/")
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json)))
                .andDo(print())
                .andExpect((status().is(200)))
                .andReturn();

        assertEquals("application/json", mock.getResponse().getContentType());

    }


    @Test
    public void putInfoWithoutToken() throws Exception{
        Profile profile = new Profile(1,1, "sdfs", "email", 910000000, "ewefwe", "3810", "aveiro", 211111111, "photo");
        JSONObject json = new JSONObject();
        json.put("id", profile.getId());
        json.put("type", profile.getType());
        json.put("name", profile.getName());
        json.put("mail", profile.getMail());
        json.put("contact", profile.getContact());
        json.put("address", profile.getAddress());
        json.put("zipCode", profile.getZipCode());
        json.put("city", profile.getCity());
        json.put("nif", profile.getNif());
        json.put("photo", profile.getPhoto());

        mockMvc.perform(put("/profile/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json)))
                .andDo(print())
                .andExpect((status().is(403)));

        MvcResult mock = mockMvc.perform(put("/profile/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json)))
                .andDo(print())
                .andExpect((status().is(403)))
                .andReturn();

        assertEquals("application/json", mock.getResponse().getContentType());
    }


    @Test
    public void deleteInfoWithoutToken() throws Exception{

        Profile profile = new Profile(1,1, "sdfs", "email", 910000000, "ewefwe", "3810", "aveiro", 211111111, "photo");
        JSONObject json = new JSONObject();
        json.put("id", profile.getId());
        json.put("type", profile.getType());
        json.put("name", profile.getName());
        json.put("mail", profile.getMail());
        json.put("contact", profile.getContact());
        json.put("address", profile.getAddress());
        json.put("zipCode", profile.getZipCode());
        json.put("city", profile.getCity());
        json.put("nif", profile.getNif());
        json.put("photo", profile.getPhoto());


        mockMvc.perform(post("/profile/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json)))
                .andDo(print())
                .andExpect((status().is(403)));

        MvcResult mock = mockMvc.perform(post("/profile/")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json)))
                .andDo(print())
                .andExpect((status().is(403)))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }


    @Test
    public void postInfoWithoutToken() throws Exception{
        mockMvc.perform(delete("/profile/"))
                .andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(delete("/profile/"))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }
}