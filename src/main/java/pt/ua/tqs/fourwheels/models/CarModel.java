package pt.ua.tqs.fourwheels.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pt.ua.tqs.fourwheels.entities.Car;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CarModel {
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
