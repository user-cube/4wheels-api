package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.models.CarModel;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.repositories.CarRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/car")

public class CarController {
    private CarRepository carRepository;
    private JwtTokenUtil jwtTokenUtil;
    private JSONObject json = new JSONObject();
    private Logger logger = LogManager.getLogger(CarController.class);

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
    public ResponseEntity<JSONObject> getAllCars(@RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);

        Page<Car> carPage =  carRepository.findCarsByCarStateEquals("selling", pageAndLimit);
        int totalPages = carPage.getTotalPages();
        List<Car> cars = carPage.getContent();

        JSONObject json = new JSONObject();
        json.put("data", cars);
        json.put("totalpages", totalPages);
        return ResponseEntity.status(HttpStatus.OK).body(json);

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
    public ResponseEntity<JSONObject> insertCar(@RequestBody CarModel car,HttpServletRequest request){

        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);
            /*if(email == null || email == ""){
                throw new Exception();
            }*/
            json.put("id", car.getCar().getId());
            json.put("photo", car.getCar().getPhoto());
            json.put("brand", car.getCar().getBrand());
            json.put("model",car.getCar().getModel());
            json.put("year", car.getCar().getYear());
            json.put("month",car.getCar().getMonth());
            json.put("description",car.getCar().getDescription());
            json.put("kilometers", car.getCar().getKilometers());
            json.put("typeOfFuel", car.getCar().getTypeOfFuel());
            json.put("ownerMail", car.getCar().getOwnerMail());
            json.put("price", car.getCar().getPrice());
            json.put("carState", car.getCar().getCarState());
            carRepository.save(car.getCar());
            return ResponseEntity.status(HttpStatus.OK).body(json);
        }catch (Exception e) {
            logger.error(e.toString());
            json.put("error","Bad credentials");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
    }


    @ApiOperation(value = "Remove a car from the database.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete a car."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<JSONObject> deleteCar(@PathVariable("id") int id,HttpServletRequest request){
        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);
            /*if(email == null || email == ""){
                throw new Exception();
            }*/
            carRepository.deleteById(id);
            json.put("success","Successfully deleted");
            return ResponseEntity.status(HttpStatus.OK).body(json);
        }catch (Exception e) {
            logger.error(e.toString());
            json.put("error","Bad credentials");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
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
    public ResponseEntity<JSONObject>  searchByBrand(@PathVariable("content") String content, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);
        Page<Car> carPage =  carRepository.findCarsByBrandContainingAndCarStateEquals(content, "selling", pageAndLimit);
        int totalPages = carPage.getTotalPages();
        List<Car> cars = carPage.getContent();

        JSONObject json = new JSONObject();
        json.put("data", cars);
        json.put("totalpages", totalPages);
        return ResponseEntity.status(HttpStatus.OK).body(json);
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
    public ResponseEntity<JSONObject> searchByModel(@PathVariable("content") String content, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);
        Page<Car> carPage =  carRepository.findCarsByModelContainingAndCarStateEquals(content, "selling", pageAndLimit);
        int totalPages = carPage.getTotalPages();
        List<Car> cars = carPage.getContent();

        JSONObject json = new JSONObject();
        json.put("data", cars);
        json.put("totalpages", totalPages);
        return ResponseEntity.status(HttpStatus.OK).body(json);
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
    public ResponseEntity<JSONObject> searchByYear(@PathVariable("content") int content, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);
        Page<Car> carPage =  carRepository.findCarsByYearEqualsAndCarStateEquals(content, "selling", pageAndLimit);
        int totalPages = carPage.getTotalPages();
        List<Car> cars = carPage.getContent();

        JSONObject json = new JSONObject();
        json.put("data", cars);
        json.put("totalpages", totalPages);
        return ResponseEntity.status(HttpStatus.OK).body(json);
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
    public ResponseEntity<JSONObject> searchByFuelType(@PathVariable("content") String content, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        Pageable pageAndLimit = PageRequest.of(page, limit);
        Page<Car> carPage =  carRepository.findCarsByTypeOfFuelEqualsAndCarStateEquals(content, "selling", pageAndLimit);
        int totalPages = carPage.getTotalPages();
        List<Car> cars = carPage.getContent();

        JSONObject json = new JSONObject();
        json.put("data", cars);
        json.put("totalpages", totalPages);
        return ResponseEntity.status(HttpStatus.OK).body(json);
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
    public ResponseEntity<JSONObject> editCarInfo(@RequestBody Car newCar, @PathVariable("id") int id, HttpServletRequest request){

        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);
            /*if(email == null || email == ""){
                throw new Exception();
            }*/

            Car optionalCar = carRepository.findCarsById(id);

            if(optionalCar.getOwnerMail().equals(email)) {
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
            }

            json.put("id", optionalCar.getId());
            json.put("photo", optionalCar.getPhoto());
            json.put("brand", optionalCar.getBrand());
            json.put("model", optionalCar.getModel());
            json.put("year", optionalCar.getYear());
            json.put("month", optionalCar.getMonth());
            json.put("description", optionalCar.getDescription());
            json.put("kilometers", optionalCar.getKilometers());
            json.put("typeOfFuel", optionalCar.getTypeOfFuel());
            json.put("ownerMail", optionalCar.getOwnerMail());
            json.put("price", optionalCar.getPrice());
            json.put("carState", optionalCar.getCarState());
            carRepository.save(optionalCar);
            return ResponseEntity.status(HttpStatus.OK).body(json);
        }catch (Exception e){
            logger.error(e.toString());
            json.put("error", "Bad credentials");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
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
    public ResponseEntity<JSONObject> markCarAsSold(@PathVariable("id") int id, HttpServletRequest request){

        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);
            /*if(email == null || email == ""){
                throw new Exception();
            }*/

            Car updateCar = carRepository.findCarsById(id);

            if(updateCar.getOwnerMail().equals(email)){
                updateCar.setCarState("sold");
                carRepository.save(updateCar);
            }else {
                throw new Exception();
            }
            json.put("id", updateCar.getId());
            json.put("photo", updateCar.getPhoto());
            json.put("brand", updateCar.getBrand());
            json.put("model", updateCar.getModel());
            json.put("year", updateCar.getYear());
            json.put("month", updateCar.getMonth());
            json.put("description", updateCar.getDescription());
            json.put("kilometers", updateCar.getKilometers());
            json.put("typeOfFuel", updateCar.getTypeOfFuel());
            json.put("ownerMail", updateCar.getOwnerMail());
            json.put("price", updateCar.getPrice());
            json.put("carState", updateCar.getCarState());
            return ResponseEntity.status(HttpStatus.OK).body(json);
        }catch (Exception e){
            logger.error(e.toString());
            json.put("error", "Bad credentials");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
    }


    /**
     * List all the cars a owner has to sell.
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
    public ResponseEntity<JSONObject> getAllCarsFromVendor(HttpServletRequest request, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Pageable pageAndLimit = PageRequest.of(page, limit);

        Page<Car> carPage =  carRepository.findCarsByOwnerMail(email, pageAndLimit);
        int totalPages = carPage.getTotalPages();
        List<Car> cars = carPage.getContent();

        JSONObject json = new JSONObject();
        json.put("data", cars);
        json.put("totalpages", totalPages);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    /**
     * List all the cars a owner has marked for sale.
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
    @GetMapping(value = "/vendor/selling")
    public ResponseEntity<JSONObject> getAllCarsOnSaleFromVendor(HttpServletRequest request, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Pageable pageAndLimit = PageRequest.of(page, limit);

        Page<Car> carPage =  carRepository.findCarsByOwnerMailEqualsAndAndCarStateEquals(email, "selling", pageAndLimit);
        int totalPages = carPage.getTotalPages();
        List<Car> cars = carPage.getContent();

        JSONObject json = new JSONObject();
        json.put("data", cars);
        json.put("totalpages", totalPages);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    /**
     * List all the cars a owner has marked for sale.
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
    @GetMapping(value = "/vendor/sold")
    public ResponseEntity<JSONObject> getAllCarsSoldFromVendor(HttpServletRequest request, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Pageable pageAndLimit = PageRequest.of(page, limit);

        Page<Car> carPage =  carRepository.findCarsByOwnerMailEqualsAndAndCarStateEquals(email, "sold", pageAndLimit);
        int totalPages = carPage.getTotalPages();
        List<Car> cars = carPage.getContent();

        JSONObject json = new JSONObject();
        json.put("data", cars);
        json.put("totalpages", totalPages);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }
}
