package pt.ua.tqs.fourwheels.entities;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.*;

@WebMvcTest(value = ProfileTest.class)
class ProfileTest {
    Profile set;
    Profile get;
    @Before
    void setUp(){
        Profile set = new Profile();
        Profile get = new Profile();
        get.setType("Vendedor");
        get.setCidade("Porto");
        get.setCodigPostal(555);
        get.setContacto(999999999);
        get.setMail("lmao@mail.com");
        get.setMorada("Rua perfeita");
        get.setName("EUSOU Lesias");
        get.setNumeroContribuinte(9412);
    }
    @Test
    void getId() {
        assertEquals(get.getId() >= 0,true);
    }

    @Test
    void getType() {
        assertEquals(get.getType(), "Vendedor");
    }

    @Test
    void getName() {
        assertEquals(get.getName(), "EUSOU Lesias");
    }

    @Test
    void getMail() {
        assertEquals(get.getMail(), "lmao@mail.com");
    }

    @Test
    void getContacto() {
        assertEquals(get.getContacto(), 999999999);
    }

    @Test
    void getMorada() {
        assertEquals(get.getMorada(), "Rua perfeita");
    }

    @Test
    void getCodigPostal() {
        assertEquals(get.getCodigPostal(), 555);
    }

    @Test
    void getCidade() {
        assertEquals(get.getType(), "Porto");
    }

    @Test
    void getNumeroContribuinte() {
        assertEquals(get.getType(), 9412);
    }

    @Test
    void setType() {
        set.setType("Vendedor");
        assertEquals(set.getType(), "Vendedor");
    }

    @Test
    void setName() {
        set.setName("DummyTest");
        assertEquals(set.getName(), "DummyTest");
    }

    @Test
    void setMail() {
        set.setMail("dummy@Test.This");
        assertEquals(set.getMail(), "dummy@Test.This");
    }

    @Test
    void setContacto() {
        set.setContacto(913913913);
        assertEquals(set.getContacto(), 913913913);
    }

    @Test
    void setMorada() {
        set.setMorada("Rua Falsa");
        assertEquals(set.getMorada(), "Rua Falsa");
    }

    @Test
    void setCodigPostal() {
        set.setCodigPostal(678798);
        assertEquals(set.getCodigPostal(), 678798);
    }

    @Test
    void setCidade() {
        set.setCidade("Nowhere");
        assertEquals(set.getCidade(), "Nowhere");
    }

    @Test
    void setNumeroContribuinte() {
        set.setNumeroContribuinte(555);
        assertEquals(set.getNumeroContribuinte(), 555);
    }
}