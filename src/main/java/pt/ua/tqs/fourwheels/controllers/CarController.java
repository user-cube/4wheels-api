package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.dto.CarDTO;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.repositories.CarRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/car")

public class CarController {
    private CarRepository carRepository;
    private JwtTokenUtil jwtTokenUtil;

    public CarController(CarRepository carRepository, JwtTokenUtil jwtTokenUtil) {
        this.carRepository = carRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }


    @ApiOperation(value = "Get car details for a specific car.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved car details information."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/{id}")
    public Car getCarInfo(@PathVariable("id") int id){
        return carRepository.findCarsById(id);
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
    public List<Car> getAllCars(@RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);
        return carRepository.findCarsByCarStateEquals("selling", pageAndLimit);
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
    public List<Car> insertCar(@RequestBody CarDTO car){
        List<Car> myList = new ArrayList<>();
        myList.add(carRepository.save(car.getCar()));
        return myList;
    }

    @ApiOperation(value = "Delete a car from the database.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete a car."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @DeleteMapping(value = "/{id}")
    public void deleteCar(@PathVariable("id") int id){
        carRepository.deleteById(id);
    }

    @ApiOperation(value = "Search a car by brand.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully search a car by brand."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/brand/{content}")
    public List<Car>  searchByBrand(@PathVariable("content") String content, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);
        return carRepository.findCarsByBrandContainingAndCarStateEquals(content, "selling", pageAndLimit);
    }

    @ApiOperation(value = "Search a car by model.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully search a car by model."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/model/{content}")
    public List<Car> searchByModel(@PathVariable("content") String content, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);
        return carRepository.findCarsByModelContainingAndCarStateEquals(content, "selling", pageAndLimit);
    }

    @ApiOperation(value = "Search a car by year.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully search a car by year."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/year/{content}")
    public List<Car> searchByYear(@PathVariable("content") int content, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);
        return carRepository.findCarsByYearEqualsAndCarStateEquals(content, "selling", pageAndLimit);
    }

    @ApiOperation(value = "Search a car by fuel.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully search a car by fuel."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/fuel/{content}")
    public List<Car> searchByFuelType(@PathVariable("content") String content, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);
        return carRepository.findCarsByTypeOfFuelEqualsAndCarStateEquals(content, "selling", pageAndLimit);
    }



    /**
     * Edit Specific Car
     * @param id
     * @return
     */
    @ApiOperation(value = "Edit car details for a specific car.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited car details information."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @PutMapping(value = "/{id}")
    public ResponseEntity<Car> editCarInfo(@RequestBody Car newCar, @PathVariable("id") int id, HttpServletRequest request){


        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);

        Car optionalCar = carRepository.findCarsById(id);

        if(optionalCar.getOwnerMail().equals(email)){
                optionalCar.setPhoto(newCar.getPhoto());
                optionalCar.setBrand(newCar.getBrand());
                optionalCar.setModel(newCar.getModel());
                optionalCar.setYear(newCar.getYear());
                optionalCar.setMonth(newCar.getMonth());
                optionalCar.setDescription(newCar.getDescription());
                optionalCar.setKilometers(newCar.getKilometers());
                optionalCar.setTypeOfFuel(newCar.getTypeOfFuel());
                optionalCar.setOwnerMail(newCar.getOwnerMail());
                optionalCar.setPrice(newCar.getPrice());

                return ResponseEntity.ok(carRepository.save(optionalCar));

        }else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }


    /**
     * Mark a car as sold.
     * @param id
     * @return
     */
    @ApiOperation(value = "Mark car as sold.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully marked car as sold."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @PutMapping(value = "sold/{id}")
    public ResponseEntity<Car> markCarAsSold(@PathVariable("id") int id, HttpServletRequest request){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);

        Car updateCar = carRepository.findCarsById(id);

        if(updateCar.getOwnerMail().equals(email)){
            updateCar.setCarState("sold");
            return ResponseEntity.ok(carRepository.save(updateCar));
        }else {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }
    }


    /**
     * List all the cars a owner as to sell.
     * @return
     */
    @ApiOperation(value = "List all the cars of a certain vendor.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the list of cars for the owner."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/vendor")
    public List<Car> getAllCarsFromVendor(HttpServletRequest request, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Pageable pageAndLimit = PageRequest.of(page, limit);

        return carRepository.findCarsByOwnerMail(email, pageAndLimit);
    }


}
