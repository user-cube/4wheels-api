package pt.ua.tqs.fourwheels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.repositories.CarRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/car")

public class CarController {

    @Autowired
    private CarRepository carRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Mono<ResponseEntity<?>> getCarInfo(@PathVariable String id) {
        try {
            return Mono.just(ResponseEntity.ok(carRepository.findById(Integer.parseInt(id))));
        } catch (IllegalArgumentException e) {
            //return Mono.just(new ResponseEntity<>("Car not found!", HttpStatus.NOT_FOUND));  //not working, trying to
            // figure out a fix
        }
    }
}
