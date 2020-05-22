package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.ua.tqs.fourwheels.entities.Car;


public interface CarRepository extends CrudRepository<Car, Integer> {
}
