package pt.ua.tqs.fourwheels.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = UsersController.class)
public class UsersControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileRepository profileRepository;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    private String accessToken = System.getenv("TEST_TOKEN");
    private String email = System.getenv("TESTER_EMAIL");

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new UsersController(profileRepository, jwtTokenUtil))
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    /**
     * GET Users Controller Tests
     * for /users/
     * with token and access level
     * @throws Exception
     */
    @Test
    public void getAllUsersWithTokenAndAuthenticationOk() throws Exception {
        Profile profile = new Profile(1,2, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, "greatphoto");

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        Page<Profile> profilePage = new PageImpl<>(profiles.subList(0, 1), Pageable.unpaged(), profiles.size());
        // Mocks
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);
        Mockito.when(profileRepository.findAll(PageRequest.of(0,1))).thenReturn(profilePage);

        mockMvc.perform(get("/users/?page=0&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")))
                .andExpect(jsonPath("$", hasKey("totalpages")));

        MvcResult mock = mockMvc.perform(get("/users/?page=0&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Users Controller Tests
     * for /users/
     * with token and without access level
     * @throws Exception
     */
    @Test
    public void getAllUsersWithTokenAndAuthenticationNotOk() throws Exception {
        Profile profile = new Profile(1,1, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, "greatphoto");

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        Page<Profile> profilePage = new PageImpl<>(profiles.subList(0, 1), Pageable.unpaged(), profiles.size());
        // Mocks
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);
        Mockito.when(profileRepository.findAll(PageRequest.of(0,1))).thenReturn(profilePage);

        mockMvc.perform(get("/users/?page=0&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/users/?page=0&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Users Controller Tests
     * for /users/
     * without token
     * @throws Exception
     */
    @Test
    public void getAllUsersWithoutToken() throws Exception {
        Profile profile = new Profile(1,1, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, "greatphoto");

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        Page<Profile> profilePage = new PageImpl<>(profiles.subList(0, 1), Pageable.unpaged(), profiles.size());
        // Mocks
        Mockito.when(profileRepository.findAll(PageRequest.of(0,1))).thenReturn(profilePage);

        mockMvc.perform(get("/users/?page=0&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/users/?page=0&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Users Controller Tests
     * for /users/buyers
     * with token and access level
     * @throws Exception
     */
    @Test
    public void getAllBuyerWithTokenAndAuthenticationOk() throws Exception {
        Profile profile = new Profile(1,2, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, "greatphoto");

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        Page<Profile> profilePage = new PageImpl<>(profiles.subList(0, 1), Pageable.unpaged(), profiles.size());
        // Mocks
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);
        Mockito.when(profileRepository.findAllByTypeEquals(0, PageRequest.of(0,1))).thenReturn(profilePage);

        mockMvc.perform(get("/users/buyers?page=0&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")))
                .andExpect(jsonPath("$", hasKey("totalpages")));

        MvcResult mock = mockMvc.perform(get("/users/buyers?page=0&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Users Controller Tests
     * for /users/buyers
     * with token and without access level
     * @throws Exception
     */
    @Test
    public void getAllBuyersWithTokenAndAuthenticationNotOk() throws Exception {
        Profile profile = new Profile(1,1, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, "greatphoto");

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        Page<Profile> profilePage = new PageImpl<>(profiles.subList(0, 1), Pageable.unpaged(), profiles.size());
        // Mocks
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);
        Mockito.when(profileRepository.findAllByTypeEquals(0, PageRequest.of(0,1))).thenReturn(profilePage);

        mockMvc.perform(get("/users/buyers?page=0&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/users/buyers?page=0&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Users Controller Tests
     * for /users/buyers
     * without token
     * @throws Exception
     */
    @Test
    public void getAllBuyersWithoutToken() throws Exception {
        Profile profile = new Profile(1,1, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, "greatphoto");

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        Page<Profile> profilePage = new PageImpl<>(profiles.subList(0, 1), Pageable.unpaged(), profiles.size());
        // Mocks
        Mockito.when(profileRepository.findAllByTypeEquals(0, PageRequest.of(0,1))).thenReturn(profilePage);

        mockMvc.perform(get("/users/buyers?page=0&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/users/buyers?page=0&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Users Controller Tests
     * for /users/vendors
     * with token and access level
     * @throws Exception
     */
    @Test
    public void getAllVendorsWithTokenAndAuthenticationOk() throws Exception {
        Profile profile = new Profile(1,2, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, "greatphoto");

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        Page<Profile> profilePage = new PageImpl<>(profiles.subList(0, 1), Pageable.unpaged(), profiles.size());
        // Mocks
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);
        Mockito.when(profileRepository.findAllByTypeEquals(1, PageRequest.of(0,1))).thenReturn(profilePage);

        mockMvc.perform(get("/users/vendors?page=0&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")))
                .andExpect(jsonPath("$", hasKey("totalpages")));

        MvcResult mock = mockMvc.perform(get("/users/vendors?page=0&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Users Controller Tests
     * for /users/vendors
     * with token and without access level
     * @throws Exception
     */
    @Test
    public void getAllVendorsWithTokenAndAuthenticationNotOk() throws Exception {
        Profile profile = new Profile(1,1, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, "greatphoto");

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        Page<Profile> profilePage = new PageImpl<>(profiles.subList(0, 1), Pageable.unpaged(), profiles.size());
        // Mocks
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);
        Mockito.when(profileRepository.findAllByTypeEquals(1, PageRequest.of(0,1))).thenReturn(profilePage);

        mockMvc.perform(get("/users/vendors?page=0&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/users/vendors?page=0&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Users Controller Tests
     * for /users/vendors
     * without token
     * @throws Exception
     */
    @Test
    public void getAllVendorsWithoutToken() throws Exception {
        Profile profile = new Profile(1,1, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, "greatphoto");

        List<Profile> profiles = new ArrayList<>();
        profiles.add(profile);
        Page<Profile> profilePage = new PageImpl<>(profiles.subList(0, 1), Pageable.unpaged(), profiles.size());
        // Mocks
        Mockito.when(profileRepository.findAllByTypeEquals(1, PageRequest.of(0,1))).thenReturn(profilePage);

        mockMvc.perform(get("/users/vendors?page=0&limit=1").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/users/vendors?page=0&limit=1").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }
}
