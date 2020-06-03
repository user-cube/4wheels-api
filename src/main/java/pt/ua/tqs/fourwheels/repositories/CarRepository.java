package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.entities.Profile;

import java.util.List;


public interface CarRepository extends CrudRepository<Car, Integer> {
    List<Car> findCarsByBrandContainingAndCarStateEquals(String brand, String state);
    List<Car> findCarsByModelContainingAndCarStateEquals(String model, String state);
    List<Car> findCarsByYearEqualsAndCarStateEquals(int year, String state);
    List<Car> findCarsByTypeOfFuelEqualsAndCarStateEquals(String fuelType, String state);
    Car findCarsById(int id);
    List<Car> findCarsByOwnerMail(String email);
    List<Car> findCarsByCarStateEquals(String state);


}
