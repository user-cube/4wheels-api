package pt.ua.tqs.fourwheels.controllers;

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

    @GetMapping("/<id>/")
    public @ResponseBody Optional<Car> getInfo(@RequestParam(value = "ID") String id){
        return carRepository.findById(Integer.parseInt(id));

}
