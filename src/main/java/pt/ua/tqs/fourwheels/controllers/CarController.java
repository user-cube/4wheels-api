package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.repositories.CarRepository;

import java.util.Optional;

@RestController
@RequestMapping("/car")

public class CarController {
    @Autowired
    private CarRepository carRepository;

    @ApiOperation(value = "Get car details for a specific car.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved car details information."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/{id}")
    public Optional<Car> getCarInfo(@PathVariable("id") int id){
        return carRepository.findById(id);
    }

    @ApiOperation(value = "Get all the cars on the database.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all cars."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/")
    public Iterable<Car> getAllCars(){
        return carRepository.findAll();
    }

    @ApiOperation(value = "Insert a car on the database.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully inserted a car."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @PostMapping(value = "/")
    public Car insertCars(@RequestBody Car car){
        Car newCar = new Car();
        newCar.setModel(car.getModel());
        newCar.setBrand(car.getBrand());
        newCar.setYear(car.getYear());
        newCar.setMonth(car.getMonth());
        newCar.setDescription(car.getDescription());
        newCar.setTypeOfFuel(car.getTypeOfFuel());
        newCar.setKilometers(car.getKilometers());
        return carRepository.save(newCar);
    }

}
