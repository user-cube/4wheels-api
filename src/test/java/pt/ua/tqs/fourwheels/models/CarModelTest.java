package pt.ua.tqs.fourwheels.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import pt.ua.tqs.fourwheels.entities.Car;

import static org.junit.Assert.assertEquals;

@WebMvcTest(value = CarModelTest.class)
public class CarModelTest {

    private CarModel carModel;

    @BeforeEach
    public void setUp(){
        carModel = new CarModel();
    }

    @Test
    public void unitTestsCarModel(){
        Car c = new Car();
        carModel.setCar(c);
        assertEquals(c, carModel.getCar());
    }

    @Test
    public void unitTestsCarModelToString(){
        Car c = new Car();
        CarModel cm = new CarModel(c);
        carModel.setCar(c);

        assertEquals(carModel.toString(), cm.toString());
    }

}



