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
        assertEquals(get.getId(), 1);
    }

    @Test
    public void getCar() {
        assertEquals(get.getCar(), 10);
    }

    @Test
    public void getMail() {
        assertEquals(get.getMail(), "omeumail@mail.com");
    }

    @Test
    public void setId() {
        set.setId(2);
        assertEquals(set.getId(), 2);
    }

    @Test
    public void setCar() {
        set.setCar(20);
        assertEquals(set.getCar(), 10);
    }

    @Test
    public void setMail() {
        set.setMail("mail@dummy.com");
        assertEquals(set.getMail(), "mail@dummy.com");
    }

    @Test
    public void setConstructor() {
        Favourite construct = new Favourite(1, "dummy@mail.com", 2);
        assertEquals(construct.getId(), 1);
        assertEquals(construct.getMail(), "dummy@mail.com");
        assertEquals(construct.getCar(), 2);
    }
}
