package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.CarRepository;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private ProfileRepository profileRepository;
    private CarRepository carRepository;
    private JwtTokenUtil jwtTokenUtil;
    private Logger logger = LogManager.getLogger(AnalyticsController.class);
    private JSONObject json = new JSONObject();
    private String auth = "Authorization";
    private String error = "error";
    private String badCredentials = "Bad Credentials";
    private String noAccess = "No Access";

    public AnalyticsController(ProfileRepository profileRepository, CarRepository carRepository , JwtTokenUtil jwtTokenUtil){
        this.profileRepository = profileRepository;
        this.carRepository = carRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @ApiOperation(value = "List general analytics from the platform.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved the analytics."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/")
    public ResponseEntity<JSONObject> getAllAnalytics(HttpServletRequest request){
        json.clear();
        try {
            String token = request.getHeader(auth).split(" ")[1];
            String email = jwtTokenUtil.getUsernameFromToken(token);
            Profile user = profileRepository.findByMail(email);
            if (user.getType() == 2) {
                long totalCars = carRepository.count();
                int totalVendors = profileRepository.countAllByTypeEquals(1);
                int totalAnalysts = profileRepository.countAllByTypeEquals(2);
                int totalBuyers = profileRepository.countAllByTypeEquals(0);
                int totalCarsOnSale = carRepository.countAllByCarStateEquals("selling");
                int totalCarsSold = carRepository.countAllByCarStateEquals("sold");

                json.put("total_cars", totalCars);
                json.put("total_vendors", totalVendors);
                json.put("total_buyers", totalBuyers);
                json.put("total_analysts", totalAnalysts);
                json.put("total_cars_selling", totalCarsOnSale);
                json.put("total_cars_sold", totalCarsSold);

                return ResponseEntity.status(HttpStatus.OK).body(json);
            } else {
                return errorAccess();
            }
        }   catch (Exception e){
            logger.error(e.toString());
            return errorCredentials();
        }
    }


    @ApiOperation(value = "List the amount of cars vendors have registered on the platform.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved amount of cars registered by vendor."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/vendors/cars/registered")
    public ResponseEntity<JSONObject> getNumberOfCarsRegisteredByVendor(HttpServletRequest request, @RequestParam(value = "limit", required=false) Integer limit){
        json.clear();
        try {
            String token = request.getHeader(auth).split(" ")[1];
            String email = jwtTokenUtil.getUsernameFromToken(token);
            Profile user = profileRepository.findByMail(email);
            List<Object> amountOfCarsRegisteredByUser;

            if (user.getType() == 2) {
                if (limit != null) {
                    amountOfCarsRegisteredByUser = profileRepository.findByOwnerCarsRegisteredLimited(limit);
                } else {
                    amountOfCarsRegisteredByUser = profileRepository.findByOwnerCarsRegistered();
                }

                return jsonFrom(amountOfCarsRegisteredByUser);
            } else {
                return errorAccess();
            }
        } catch (Exception e){
            logger.error(e.toString());
            return errorCredentials();
        }
    }

    @ApiOperation(value = "List the amount of cars vendors have sold on the platform.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved amount of cars sold by vendor."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/vendors/cars/sold")
    public ResponseEntity<JSONObject> getNumberOfCarsSoldByVendor(HttpServletRequest request, @RequestParam(value = "limit", required=false) Integer limit){
        json.clear();
        try {
            String token = request.getHeader(auth).split(" ")[1];
            String email = jwtTokenUtil.getUsernameFromToken(token);
            Profile user = profileRepository.findByMail(email);
            List<Object> amountOfCarsSoldByUser;

            if (user.getType() == 2) {
                if (limit != null) {
                    amountOfCarsSoldByUser =  profileRepository.findByOwnerCarsSoldLimited(limit);
                } else {
                    amountOfCarsSoldByUser =  profileRepository.findByOwnerCarsSold();
                }

                return jsonFrom(amountOfCarsSoldByUser);
            } else {
                return errorAccess();
            }
        } catch (Exception e){
            logger.error(e.toString());
            return errorCredentials();
        }
    }

    @ApiOperation(value = "List the amount of cars vendors have for sale on the platform.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved amount of cars for sale by vendor."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/vendors/cars/selling")
    public ResponseEntity<JSONObject> getNumberOfCarsOnSaleByVendor(HttpServletRequest request, @RequestParam(value = "limit", required=false) Integer limit){
        json.clear();
        try {
            String token = request.getHeader(auth).split(" ")[1];
            String email = jwtTokenUtil.getUsernameFromToken(token);
            Profile user = profileRepository.findByMail(email);
            List<Object> amountOfCarsOnSaleByUser;

            if (user.getType() == 2) {
                if (limit != null) {
                    amountOfCarsOnSaleByUser =  profileRepository.findByOwnerCarsSellingLimited(limit);
                } else {
                    amountOfCarsOnSaleByUser =  profileRepository.findByOwnerCarsSelling();
                }
                return jsonFrom(amountOfCarsOnSaleByUser);
            } else {
                return errorAccess();
            }
        } catch (Exception e){
            logger.error(e.toString());
            return errorCredentials();
        }
    }
    private ResponseEntity<JSONObject> errorAccess() {
        json.put(error, noAccess);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
    }

    private ResponseEntity<JSONObject> errorCredentials() {
        json.put(error, badCredentials);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
    }

    private ResponseEntity<JSONObject> jsonFrom(List<Object> list){
        json.put("data", list);
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }
}
