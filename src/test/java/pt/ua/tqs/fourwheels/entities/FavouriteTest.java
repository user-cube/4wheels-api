package pt.ua.tqs.fourwheels.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@WebMvcTest(value = ProfileTest.class)
public class FavouriteTest {
    private Favourite get;
    private Favourite set;

    @BeforeEach
    public void setUp() {
        set = new Favourite();
        get = new Favourite();
        get.setId(1);
        get.setCar(10);
        get.setMail("omeumail@mail.com");
    }

    @Test
    public void getId() {
        assertEquals(1,get.getId());
    }

    @Test
    public void getCar() {
        assertEquals(10,get.getCar());
    }

    @Test
    public void getMail() {
        assertEquals("omeumail@mail.com",get.getMail());
    }

    @Test
    public void setId() {
        set.setId(2);
        assertEquals(2,set.getId());
    }

    @Test
    public void setCar() {
        set.setCar(20);
        assertEquals(20,set.getCar());
    }

    @Test
    public void setMail() {
        set.setMail("mail@dummy.com");
        assertEquals("mail@dummy.com",set.getMail());
    }

    @Test
    public void setConstructor() {
        Favourite construct = new Favourite(1, "dummy@mail.com", 2);
        assertEquals(1,construct.getId());
        assertEquals("dummy@mail.com",construct.getMail());
        assertEquals(2,construct.getCar());
    }
}
