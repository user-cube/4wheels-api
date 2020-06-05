package pt.ua.tqs.fourwheels.models;

import pt.ua.tqs.fourwheels.entities.Car;


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
