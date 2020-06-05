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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Favourite;
import pt.ua.tqs.fourwheels.repositories.FavouriteRepository;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = FavouriteController.class)
public class FavouriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FavouriteRepository favouriteRepository;
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    String accessToken = System.getenv("TEST_TOKEN");
    String email = System.getenv("TESTER_EMAIL");

    private String auth = "Authorization";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FavouriteController(favouriteRepository, jwtTokenUtil))
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    /**
     * GET Favourite Controller Tests
     * with and without token
     * @throws Exception
     */
    @Test
    public void getFavouritesWithToken() throws Exception{
        // Mocks
        Favourite fav = new Favourite(1, "dummy@mail.com", 2);
        Favourite favourite = new Favourite(2, "dummy@mail.com", 5);
        List<Favourite> favouriteList = new ArrayList();
        favouriteList.add(fav);
        favouriteList.add(favourite);
        Mockito.when(favouriteRepository.findById(1)).thenReturn(java.util.Optional.of(fav));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(favouriteRepository.findAllByMail(email)).thenReturn(favouriteList);

        mockMvc.perform(get("/favourite/").header(auth, "Bearer " + accessToken)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasKey("data")));
        MvcResult mock = mockMvc.perform(get("/favourite/").header(auth, "Bearer " + accessToken))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals("application/json",
                mock.getResponse().getContentType());
    }
    @Test
    public void getFavouritesWithoutToken() throws Exception{
        // Mocks
        Favourite fav = new Favourite(1, "dummy@mail.com", 2);
        Favourite favourite = new Favourite(2, "dummy@mail.com", 5);
        List<Favourite> favouriteList = new ArrayList();
        favouriteList.add(fav);
        favouriteList.add(favourite);

        mockMvc.perform(get("/favourite/")).andDo(print())
                .andExpect(status().is(403));
        MvcResult mock = mockMvc.perform(get("/favourite/"))
                .andExpect(status().is(403))
                .andReturn();
        assertEquals("application/json",
                mock.getResponse().getContentType());
    }



    /**
     * POST Favourite Controller Tests
     * with and without token
     * @throws Exception
     */
    @Test
    public void postFavouriteWithToken() throws Exception{
        // Mocks
        Favourite fav = new Favourite(1, "dummy@mail.com", 2);
        Favourite favourite = new Favourite(2, "dummy@mail.com", 5);
        List<Favourite> favouriteList = new ArrayList();
        favouriteList.add(fav);
        favouriteList.add(favourite);
        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();

        json2.put("favourites", favouriteList);

        Mockito.when(favouriteRepository.findById(1)).thenReturn(java.util.Optional.of(fav));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(favouriteRepository.findAllByMail(email)).thenReturn(favouriteList);

        mockMvc.perform(post("/favourite/1")
                .header(auth, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json2)))
                .andDo(print())
                .andExpect((status().is(200)));
        MvcResult mock = mockMvc.perform(post("/favourite/1")
                .header(auth, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json2)))
                .andDo(print())
                .andExpect((status().is(200)))
                .andReturn();

        assertEquals("application/json", mock.getResponse().getContentType());
    }
    @Test
    public void postFavouriteWithoutToken() throws Exception{
        // Mocks
        Favourite fav = new Favourite(1, "dummy@mail.com", 2);
        Favourite favourite = new Favourite(2, "dummy@mail.com", 5);
        List<Favourite> favouriteList = new ArrayList();
        favouriteList.add(fav);
        favouriteList.add(favourite);
        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();

        json2.put("favourites", favouriteList);

        mockMvc.perform(post("/favourite/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json2)))
                .andDo(print())
                .andExpect((status().is(403)));
        MvcResult mock = mockMvc.perform(post("/favourite/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json2)))
                .andDo(print())
                .andExpect((status().is(403)))
                .andReturn();

        assertEquals("application/json", mock.getResponse().getContentType());
    }



    /**
     * DELETE Favourite Controller Tests
     * with and without token
     * @throws Exception
     */
    @Test
    public void deleteFavouriteWithToken() throws Exception{
        // Mocks
        Favourite fav = new Favourite(1, "dummy@mail.com", 2);
        Favourite favourite = new Favourite(2, "dummy@mail.com", 5);
        List<Favourite> favouriteList = new ArrayList();
        favouriteList.add(fav);
        favouriteList.add(favourite);
        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();

        json2.put("favourites", favouriteList);

        Mockito.when(favouriteRepository.findById(1)).thenReturn(java.util.Optional.of(fav));
        Mockito.when(jwtTokenUtil.getUsernameFromToken(accessToken)).thenReturn(email);
        Mockito.when(favouriteRepository.findAllByMail(email)).thenReturn(favouriteList);

        mockMvc.perform(delete("/favourite/1")
                .header(auth, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json2)))
                .andDo(print())
                .andExpect((status().is(200)));
        MvcResult mock = mockMvc.perform(delete("/favourite/1")
                .header(auth, "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json2)))
                .andDo(print())
                .andExpect((status().is(200)))
                .andReturn();

        assertEquals("application/json", mock.getResponse().getContentType());
    }
    @Test
    public void deleteFavouriteWithoutToken() throws Exception{
        // Mocks
        Favourite fav = new Favourite(1, "dummy@mail.com", 2);
        Favourite favourite = new Favourite(2, "dummy@mail.com", 5);
        List<Favourite> favouriteList = new ArrayList();
        favouriteList.add(fav);
        favouriteList.add(favourite);
        JSONObject json1 = new JSONObject();
        JSONObject json2 = new JSONObject();

        json2.put("favourites", favouriteList);

        mockMvc.perform(delete("/favourite/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json2)))
                .andDo(print())
                .andExpect((status().is(403)));
        MvcResult mock = mockMvc.perform(delete("/favourite/1")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8").content(String.valueOf(json2)))
                .andDo(print())
                .andExpect((status().is(403)))
                .andReturn();

        assertEquals("application/json", mock.getResponse().getContentType());
    }
}
