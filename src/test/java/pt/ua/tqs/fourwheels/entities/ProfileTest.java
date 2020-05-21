package pt.ua.tqs.fourwheels.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.*;

@WebMvcTest(value = ProfileTest.class)
class ProfileTest {
    private Profile set;
    private Profile get;

    @BeforeEach
    public void setUp(){
        set = new Profile();
        get = new Profile();
        get.setId(1);
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
    public void getId() {
        assertEquals(get.getId() >= 0,true);
    }

    @Test
    public void getType() {
        assertEquals(get.getType(), "Vendedor");
    }

    @Test
    public void getName() {
        assertEquals(get.getName(), "EUSOU Lesias");
    }

    @Test
    public void getMail() {
        assertEquals(get.getMail(), "lmao@mail.com");
    }

    @Test
    public void getContacto() {
        assertEquals(get.getContacto(), 999999999);
    }

    @Test
    public void getMorada() {
        assertEquals(get.getMorada(), "Rua perfeita");
    }

    @Test
    public void getCodigPostal() {
        assertEquals(get.getCodigPostal(), 555);
    }

    @Test
    public void getCidade() {
        assertEquals(get.getCidade(), "Porto");
    }

    @Test
    public void getNumeroContribuinte() {
        assertEquals(get.getNumeroContribuinte(), 9412);
    }

    @Test
    public void setType() {
        set.setType("Vendedor");
        assertEquals(set.getType(), "Vendedor");
    }

    @Test
    public void setName() {
        set.setName("DummyTest");
        assertEquals(set.getName(), "DummyTest");
    }

    @Test
    public void setMail() {
        set.setMail("dummy@Test.This");
        assertEquals(set.getMail(), "dummy@Test.This");
    }

    @Test
    public void setContacto() {
        set.setContacto(913913913);
        assertEquals(set.getContacto(), 913913913);
    }

    @Test
    public void setMorada() {
        set.setMorada("Rua Falsa");
        assertEquals(set.getMorada(), "Rua Falsa");
    }

    @Test
    public void setCodigPostal() {
        set.setCodigPostal(678798);
        assertEquals(set.getCodigPostal(), 678798);
    }

    @Test
    public void setCidade() {
        set.setCidade("Nowhere");
        assertEquals(set.getCidade(), "Nowhere");
    }

    @Test
    public void setNumeroContribuinte() {
        set.setNumeroContribuinte(555);
        assertEquals(set.getNumeroContribuinte(), 555);
    }
}