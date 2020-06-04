package pt.ua.tqs.fourwheels.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(value = CarTest.class)
class CarTest {
    private Car set;
    private Car get;

    @BeforeEach
    public void setUp(){
        set = new Car();
        get = new Car();
        get.setCarState("sold");
        get.setId(1);
        get.setPhoto("IMAGEMFANTAASTICA");
        get.setBrand("Mazda");
        get.setModel("555");
        get.setYear(1999);
        get.setMonth(5);
        get.setDescription("EUSOU Lesias");
        get.setKilometers(9412);
        get.setTypeOfFuel("Gasolina");
        get.setOwnerMail("BestMail@EU.pt");
        get.setPrice(12003);
    }

    @Test
    void getCarState() {
        assertEquals("sold",get.getCarState());
    }

    @Test
    void setCarState() {
        set.setCarState("Selling");
        assertEquals("selling", set.getCarState());
    }

    @Test
    void getId() {
        assertEquals(true, get.getId() >= 0);
    }

    @Test
    void setId() {
        set.setId(2);
        assertEquals(true,set.getId() == 2);
    }

    @Test
    void getPhoto() {
        assertEquals("IMAGEMFANTAASTICA",get.getPhoto());
    }

    @Test
    void setPhoto() {
        set.setPhoto("image2.0");
        assertEquals("image2.0",set.getPhoto());
    }

    @Test
    void getBrand() {
        assertEquals("Mazda",get.getBrand());
    }

    @Test
    void setBrand() {
        set.setBrand("Audi");
        assertEquals("Audi",set.getBrand());
    }

    @Test
    void getModel() {
        assertEquals("555",get.getModel());
    }

    @Test
    void setModel() {
        set.setModel("CX777");
        assertEquals("CX777",set.getModel());
    }

    @Test
    void getYear() {
        assertEquals(1999,get.getYear());
    }

    @Test
    void setYear() {
        set.setYear(20200);
        assertEquals(20200, set.getYear());
    }

    @Test
    void getMonth() {
        assertEquals(5,get.getMonth());
    }

    @Test
    void setMonth() {
        set.setMonth(5);
        assertEquals(5,set.getMonth());
    }

    @Test
    void getDescription() {
        assertEquals("EUSOU Lesias",get.getDescription());
    }

    @Test
    void setDescription() {
        set.setDescription("Hello please buy my car!");
        assertEquals("Hello please buy my car!",set.getDescription());
    }

    @Test
    void getKilometers() {
        assertEquals(9412,get.getKilometers());
    }

    @Test
    void setKilometers() {
        set.setKilometers(999999999);
        assertEquals(999999999,set.getKilometers());
    }

    @Test
    void getTypeOfFuel() {
        assertEquals("Gasolina",get.getTypeOfFuel());
    }

    @Test
    void setTypeOfFuel() {
        set.setTypeOfFuel("Electric");
        assertEquals("Electric",set.getTypeOfFuel());
    }

    @Test
    void getOwnerMail() {
        assertEquals("BestMail@EU.pt",get.getOwnerMail());
    }

    @Test
    void setOwnerMail() {
        set.setOwnerMail("MyMail@mail.ua.pt");
        assertEquals("MyMail@mail.ua.pt",set.getOwnerMail());
    }

    @Test
    void getPrice() {
        assertEquals(12003,get.getPrice(),10);
    }

    @Test
    void setPrice() {
        set.setPrice(999999999);
        assertEquals(999999999,set.getPrice(),10);
    }
}