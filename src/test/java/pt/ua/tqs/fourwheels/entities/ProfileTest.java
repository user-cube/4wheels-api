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
        get.setPhoto("lindo");
    }

    @Test
    public void constProfilePartial() {
        Profile p = new Profile(1, "name", "mail", 1, "address", "zipCode", "city", 1, "photo");
        assertEquals(1, p.getType());
        assertEquals("name", p.getName());
        assertEquals("mail",p.getMail());
        assertEquals(1,p.getContact());
        assertEquals("address", p.getAddress());
        assertEquals("zipCode", p.getZipCode());
        assertEquals("city", p.getCity());
        assertEquals(1, p.getNif());
        assertEquals("photo", p.getPhoto());

    }

    @Test
    public void constProfileFull(){
        Profile p = new Profile(1, 1, "name", "mail", 1, "address", "zipCode", "city", 1, "photo");
        assertEquals(1, p.getId());
        assertEquals(1, p.getType());
        assertEquals("name", p.getName());
        assertEquals("mail", p.getMail());
        assertEquals(1, p.getContact());
        assertEquals("address", p.getAddress());
        assertEquals("zipCode", p.getZipCode());
        assertEquals("city", p.getCity());
        assertEquals(1, p.getNif());
        assertEquals("photo", p.getPhoto());
    }

    @Test
    public void getId() {
        assertEquals(true, get.getId() >= 0);
    }

    @Test
    public void getType() {
        assertEquals(1, get.getType());
    }

    @Test
    public void getName() {
        assertEquals("EUSOU Lesias", get.getName());
    }

    @Test
    public void getMail() {
        assertEquals("lmao@mail.com", get.getMail());
    }

    @Test
    public void getContact() {
        assertEquals(999999999, get.getContact());
    }

    @Test
    public void getAdress() {
        assertEquals("Rua perfeita", get.getAddress());
    }

    @Test
    public void getZipCode() {
        assertEquals("555", get.getZipCode());
    }

    @Test
    public void getCity() {
        assertEquals("Porto", get.getCity());
    }

    @Test
    public void getNif() {
        assertEquals(9412, get.getNif());
    }

    @Test
    public void setPhoto() {
        assertEquals("lindo", get.getPhoto());
    }

    @Test
    public void setType() {
        set.setType(1);
        assertEquals(1, set.getType());
    }

    @Test
    public void setName() {
        set.setName("DummyTest");
        assertEquals("DummyTest",set.getName());
    }

    @Test
    public void setMail() {
        set.setMail("dummy@Test.This");
        assertEquals("dummy@Test.This",set.getMail());
    }

    @Test
    public void setContact() {
        set.setContact(913913913);
        assertEquals(913913913,set.getContact());
    }

    @Test
    public void setAddress() {
        set.setAddress("Rua Falsa");
        assertEquals("Rua Falsa",set.getAddress());
    }

    @Test
    public void setZipCode() {
        set.setZipCode("678798");
        assertEquals("678798",set.getZipCode());
    }

    @Test
    public void setCity() {
        set.setCity("Nowhere");
        assertEquals("Nowhere",set.getCity());
    }

    @Test
    public void setNif() {
        set.setNif(555);
        assertEquals(555,set.getNif());
    }
}