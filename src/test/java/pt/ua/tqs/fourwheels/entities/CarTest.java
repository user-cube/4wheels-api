package pt.ua.tqs.fourwheels.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.assertEquals;

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
    public void getCarState() {
        assertEquals("sold",get.getCarState());
    }

    @Test
    public void setCarState() {
        set.setCarState("selling");
        assertEquals("selling", set.getCarState());
    }

    @Test
    public void getId() {
        assertEquals(true, get.getId() >= 0);
    }

    @Test
    public void setId() {
        set.setId(2);
        assertEquals(true,set.getId() == 2);
    }

    @Test
    public void getPhoto() {
        assertEquals("IMAGEMFANTAASTICA",get.getPhoto());
    }

    @Test
    public void setPhoto() {
        set.setPhoto("image2.0");
        assertEquals("image2.0",set.getPhoto());
    }

    @Test
    public void getBrand() {
        assertEquals("Mazda",get.getBrand());
    }

    @Test
    public void setBrand() {
        set.setBrand("Audi");
        assertEquals("Audi",set.getBrand());
    }

    @Test
    public void getModel() {
        assertEquals("555",get.getModel());
    }

    @Test
    public void setModel() {
        set.setModel("CX777");
        assertEquals("CX777",set.getModel());
    }

    @Test
    public void getYear() {
        assertEquals(1999,get.getYear());
    }

    @Test
    public void setYear() {
        set.setYear(20200);
        assertEquals(20200, set.getYear());
    }

    @Test
    public void getMonth() {
        assertEquals(5,get.getMonth());
    }

    @Test
    public void setMonth() {
        set.setMonth(5);
        assertEquals(5,set.getMonth());
    }

    @Test
    public void getDescription() {
        assertEquals("EUSOU Lesias",get.getDescription());
    }

    @Test
    public void setDescription() {
        set.setDescription("Hello please buy my car!");
        assertEquals("Hello please buy my car!",set.getDescription());
    }

    @Test
    public void getKilometers() {
        assertEquals(9412,get.getKilometers());
    }

    @Test
    public void setKilometers() {
        set.setKilometers(999999999);
        assertEquals(999999999,set.getKilometers());
    }

    @Test
    public void getTypeOfFuel() {
        assertEquals("Gasolina",get.getTypeOfFuel());
    }

    @Test
    public void setTypeOfFuel() {
        set.setTypeOfFuel("Electric");
        assertEquals("Electric",set.getTypeOfFuel());
    }

    @Test
    public void getOwnerMail() {
        assertEquals("BestMail@EU.pt",get.getOwnerMail());
    }

    @Test
    public void setOwnerMail() {
        set.setOwnerMail("MyMail@mail.ua.pt");
        assertEquals("MyMail@mail.ua.pt",set.getOwnerMail());
    }

    @Test
    public void getPrice() {
        assertEquals(12003,get.getPrice(),10);
    }

    @Test
    public void setPrice() {
        set.setPrice(999999999);
        assertEquals(999999999,set.getPrice(),10);
    }
}