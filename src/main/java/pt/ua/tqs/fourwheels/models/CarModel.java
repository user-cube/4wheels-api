package pt.ua.tqs.fourwheels.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.entities.Profile;


public class CarModel {
    private Car car;

    public CarModel() {}
    public CarModel(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "CarModel{" +
                "car=" + car +
                '}';
    }
}
