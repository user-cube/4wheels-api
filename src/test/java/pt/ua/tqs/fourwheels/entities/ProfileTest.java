package pt.ua.tqs.fourwheels.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.assertEquals;

@WebMvcTest(value = ProfileTest.class)
class ProfileTest {
    private Profile set;
    private Profile get;

    @BeforeEach
    public void setUp(){
        set = new Profile();
        get = new Profile();
        get.setId(1);
        get.setType(1);
        get.setCity("Porto");
        get.setZipCode("555");
        get.setContact(999999999);
        get.setMail("lmao@mail.com");
        get.setAddress("Rua perfeita");
        get.setName("EUSOU Lesias");
        get.setNif(9412);
    }
    @Test
    public void getId() {
        assertEquals(get.getId() >= 0,true);
    }

    @Test
    public void getType() {
        assertEquals(get.getType(), 1);
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
    public void getContact() {
        assertEquals(get.getContact(), 999999999);
    }

    @Test
    public void getAdress() {
        assertEquals(get.getAddress(), "Rua perfeita");
    }

    @Test
    public void getZipCode() {
        assertEquals(get.getZipCode(), "555");
    }

    @Test
    public void getCity() {
        assertEquals(get.getCity(), "Porto");
    }

    @Test
    public void getNif() {
        assertEquals(get.getNif(), 9412);
    }

    @Test
    public void setType() {
        set.setType(1);
        assertEquals(set.getType(), 1);
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
    public void setContact() {
        set.setContact(913913913);
        assertEquals(set.getContact(), 913913913);
    }

    @Test
    public void setAddress() {
        set.setAddress("Rua Falsa");
        assertEquals(set.getAddress(), "Rua Falsa");
    }

    @Test
    public void setZipCode() {
        set.setZipCode("678798");
        assertEquals(set.getZipCode(), "678798");
    }

    @Test
    public void setCity() {
        set.setCity("Nowhere");
        assertEquals(set.getCity(), "Nowhere");
    }

    @Test
    public void setNif() {
        set.setNif(555);
        assertEquals(set.getNif(), 555);
    }
}