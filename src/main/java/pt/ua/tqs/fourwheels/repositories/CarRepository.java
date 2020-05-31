package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.ua.tqs.fourwheels.entities.Car;

import java.util.List;


public interface CarRepository extends CrudRepository<Car, Integer> {
    List<Car> findCarsByBrandContaining(String brand);
    List<Car> findCarsByModelContaining(String model);
    List<Car> findCarsByYearEquals(int year);
    List<Car> findCarsByTypeOfFuelEquals(String fuelType);
    Car findCarsById(int id);

}
