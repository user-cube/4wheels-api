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
        get.setCarState("Sold");
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
        assertEquals(get.getCarState(),"Sold");
    }

    @Test
    void setCarState() {
        set.setCarState("Selling");
        assertEquals(set.getCarState(), "Selling");
    }

    @Test
    void getId() {
        assertEquals(get.getId() >= 0, true);
    }

    @Test
    void setId() {
        set.setId(2);
        assertEquals(set.getId() == 2,true);
    }

    @Test
    void getPhoto() {
        assertEquals(get.getPhoto(), "IMAGEMFANTAASTICA");
    }

    @Test
    void setPhoto() {
        set.setPhoto("image2.0");
        assertEquals(set.getPhoto(), "image2.0");
    }

    @Test
    void getBrand() {
        assertEquals(get.getBrand(), "Mazda");
    }

    @Test
    void setBrand() {
        set.setBrand("Audi");
        assertEquals(set.getBrand(),"Audi");
    }

    @Test
    void getModel() {
        assertEquals(get.getModel(), "555");
    }

    @Test
    void setModel() {
        set.setModel("CX777");
        assertEquals(set.getModel(), "CX777");
    }

    @Test
    void getYear() {
        assertEquals(get.getYear(),1999);
    }

    @Test
    void setYear() {
        set.setYear(20200);
        assertEquals(set.getYear(), 20200);
    }

    @Test
    void getMonth() {
        assertEquals(get.getMonth(),5);
    }

    @Test
    void setMonth() {
        set.setMonth(5);
        assertEquals(set.getMonth(),5);
    }

    @Test
    void getDescription() {
        assertEquals(get.getDescription(),"EUSOU Lesias");
    }

    @Test
    void setDescription() {
        set.setDescription("Hello please buy my car!");
        assertEquals(set.getDescription(),"Hello please buy my car!");
    }

    @Test
    void getKilometers() {
        assertEquals(get.getKilometers(),9412);
    }

    @Test
    void setKilometers() {
        set.setKilometers(999999999);
        assertEquals(set.getKilometers(),999999999);
    }

    @Test
    void getTypeOfFuel() {
        assertEquals(get.getTypeOfFuel(),"Gasolina");
    }

    @Test
    void setTypeOfFuel() {
        set.setTypeOfFuel("Electric");
        assertEquals(set.getTypeOfFuel(),"Electric");
    }

    @Test
    void getOwnerMail() {
        assertEquals(get.getOwnerMail(),"BestMail@EU.pt");
    }

    @Test
    void setOwnerMail() {
        set.setOwnerMail("MyMail@mail.ua.pt");
        assertEquals(set.getOwnerMail(),"MyMail@mail.ua.pt");
    }

    @Test
    void getPrice() {
        assertEquals(get.getPrice(),12003,10);
    }

    @Test
    void setPrice() {
        set.setPrice(999999999);
        assertEquals(set.getPrice(),999999999,10);
    }
}