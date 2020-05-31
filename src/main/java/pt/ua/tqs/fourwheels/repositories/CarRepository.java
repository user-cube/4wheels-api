package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import pt.ua.tqs.fourwheels.entities.Car;

import java.util.List;


public interface CarRepository extends PagingAndSortingRepository<Car, Integer> {
    List<Car> findCarsByBrandContainingAndCarStateEquals(String brand, String state, Pageable pageable);
    List<Car> findCarsByModelContainingAndCarStateEquals(String model, String state, Pageable pageable);
    List<Car> findCarsByYearEqualsAndCarStateEquals(int year, String state, Pageable pageable);
    List<Car> findCarsByTypeOfFuelEqualsAndCarStateEquals(String fuelType, String state, Pageable pageable);
    Car findCarsById(int id);
    List<Car> findCarsByOwnerMail(String email, Pageable pageable);
    List<Car> findCarsByCarStateEquals(String state, Pageable pageable);
}
