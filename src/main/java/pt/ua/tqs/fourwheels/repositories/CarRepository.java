package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import pt.ua.tqs.fourwheels.entities.Car;


public interface CarRepository extends PagingAndSortingRepository<Car, Integer> {
    Page<Car> findCarsByBrandContainingAndCarStateEquals(String brand, String state, Pageable pageable);
    Page<Car> findCarsByModelContainingAndCarStateEquals(String model, String state, Pageable pageable);
    Page<Car> findCarsByYearEqualsAndCarStateEquals(int year, String state, Pageable pageable);
    Page<Car> findCarsByTypeOfFuelEqualsAndCarStateEquals(String fuelType, String state, Pageable pageable);
    Car findCarsById(int id);
    Page<Car> findCarsByOwnerMail(String email, Pageable pageable);
    Page<Car> findCarsByCarStateEquals(String state, Pageable pageable);
    Page<Car> findCarsByOwnerMailEqualsAndAndCarStateEquals(String email, String sate, Pageable pageable);
}
