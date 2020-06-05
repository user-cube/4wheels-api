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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.CarRepository;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = AnalyticsController.class)
public class AnalyticsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProfileRepository profileRepository;
    @MockBean
    private CarRepository carRepository;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;
    private String accessToken = System.getenv("TEST_TOKEN");
    private String email = System.getenv("TESTER_EMAIL");

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new AnalyticsController(profileRepository, carRepository, jwtTokenUtil))
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/
     * with token and access level
     * @throws Exception
     */
    @Test
    public void getAllAnalyticsWithTokenAndAuthenticationOk() throws Exception {
        // Mocks
        Profile profile = new Profile(1,2, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, null);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(get("/analytics/").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk());

        MvcResult mock = mockMvc.perform(get("/analytics/").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/
     * with token and without access level
     * @throws Exception
     */
    @Test
    public void getAllAnalyticsWithTokenAndAuthenticationNotOk() throws Exception {
        // Mocks
        Profile profile = new Profile(1,1, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, null);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(get("/analytics/").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/analytics/").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json", mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/
     * without token
     * @throws Exception
     */
    @Test
    public void getAllAnalyticsWithoutToken() throws Exception {
        mockMvc.perform(get("/analytics/")).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/analytics/"))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json", mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/vendors/cars/registered
     * with token and access level
     * @throws Exception
     */
    @Test
    public void getNumberOfCarsRegisteredByVendorWithTokenAndAuthenticationOk() throws Exception {
        // Mocks
        Profile profile = new Profile(1,2, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, null);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(get("/analytics/vendors/cars/registered").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk());

        MvcResult mock = mockMvc.perform(get("/analytics/vendors/cars/registered").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/vendors/cars/registered
     * with token and without access level
     * @throws Exception
     */
    @Test
    public void getNumberOfCarsRegisteredByVendorWithTokenAndAuthenticationNotOk() throws Exception {
        // Mocks
        Profile profile = new Profile(1,0, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, null);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(get("/analytics/vendors/cars/registered").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/analytics/vendors/cars/registered").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/vendors/cars/registered
     * without token
     * @throws Exception
     */
    @Test
    public void getNumberOfCarsRegisteredByVendorWithoutToken() throws Exception {
        mockMvc.perform(get("/analytics/vendors/cars/registered").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/analytics/vendors/cars/registered").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/vendors/cars/sold
     * with token and access level
     * @throws Exception
     */
    @Test
    public void getNumberOfCarsSoldByVendorWithTokenAndAuthenticationOk() throws Exception {
        // Mocks
        Profile profile = new Profile(1,2, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, null);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(get("/analytics/vendors/cars/sold").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk());

        MvcResult mock = mockMvc.perform(get("/analytics/vendors/cars/sold").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/vendors/cars/sold
     * with token and without access level
     * @throws Exception
     */
    @Test
    public void getNumberOfCarsSoldByVendorWithTokenAndAuthenticationNotOk() throws Exception {
        // Mocks
        Profile profile = new Profile(1,0, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, null);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(get("/analytics/vendors/cars/sold").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/analytics/vendors/cars/sold").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/vendors/cars/sold
     * without token
     * @throws Exception
     */
    @Test
    public void getNumberOfCarsSoldByVendorWithoutToken() throws Exception {
        mockMvc.perform(get("/analytics/vendors/cars/sold").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/analytics/vendors/cars/sold").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/vendors/cars/selling
     * with token and access level
     * @throws Exception
     */
    @Test
    public void getNumberOfCarsOnSaleByVendorWithTokenAndAuthenticationOk() throws Exception {
        // Mocks
        Profile profile = new Profile(1,2, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, null);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(get("/analytics/vendors/cars/selling").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk());

        MvcResult mock = mockMvc.perform(get("/analytics/vendors/cars/selling").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/vendors/cars/registered
     * with token and without access level
     * @throws Exception
     */
    @Test
    public void getNumberOfCarsOnSaleByVendorWithTokenAndAuthenticationNotOk() throws Exception {
        // Mocks
        Profile profile = new Profile(1,0, "sdfs", email, 910000000, "ewefwe", "3810", "aveiro", 211111111, null);
        Mockito.when(profileRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(profile));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(profileRepository.findByMail(email)).thenReturn(profile);

        mockMvc.perform(get("/analytics/vendors/cars/selling").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/analytics/vendors/cars/selling").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

    /**
     * GET Analytics Controller Tests
     * for /analytics/vendors/cars/registered
     * without token
     * @throws Exception
     */
    @Test
    public void getNumberOfCarsOnSaleByVendorWithoutToken() throws Exception {
        mockMvc.perform(get("/analytics/vendors/cars/selling").header("Authorization", "Bearer " + accessToken)).andDo(print())
                .andExpect(status().is(403));

        MvcResult mock = mockMvc.perform(get("/analytics/vendors/cars/selling").header("Authorization", "Bearer " + accessToken))
                .andExpect(status().is(403))
                .andReturn();

        assertEquals("application/json",
                mock.getResponse().getContentType());
    }

}
