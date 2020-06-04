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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Favourite;
import pt.ua.tqs.fourwheels.repositories.FavouriteRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;


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

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FavouriteController(favouriteRepository, jwtTokenUtil))
                .alwaysExpect(forwardedUrl(null))
                .build();
    }

    @Test
    public void getFavouritesWithTokenOk() {
        Favourite fav = new Favourite(1, "dummy@mail.com", 2);
        Mockito.when(favouriteRepository.findById(1)).thenReturn(java.util.Optional.of(fav));
    }


}
