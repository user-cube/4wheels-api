package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {
    private ProfileRepository profileRepository;
    private JwtTokenUtil jwtTokenUtil;

    public UsersController(ProfileRepository profileRepository, JwtTokenUtil jwtTokenUtil){
        this.profileRepository = profileRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @ApiOperation(value = "List all the users registered on the platform.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all users."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/")
    public ResponseEntity<JSONObject> getAllUsers(HttpServletRequest request, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Profile user = profileRepository.findByMail(email);
        Pageable pageAndLimit = PageRequest.of(page, limit);

        if (user.getType() == 2) {
            Page<Profile> userPage = profileRepository.findAll(pageAndLimit);
            int totalPages = userPage.getTotalPages();
            List<Profile> userOfPage = userPage.getContent();

            JSONObject json = new JSONObject();
            json.put("data", userOfPage);
            json.put("totalpages", totalPages);

            return ResponseEntity.status(HttpStatus.OK).body(json);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @ApiOperation(value = "List all the buyers registered on the platform.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all buyers."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/buyers")
    public ResponseEntity<JSONObject> getAllBuyers(HttpServletRequest request, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Profile user = profileRepository.findByMail(email);
        Pageable pageAndLimit = PageRequest.of(page, limit);

        if (user.getType() == 2) {
            Page<Profile> userPage =  profileRepository.findAllByTypeEquals(0,pageAndLimit);
            int totalPages = userPage.getTotalPages();
            List<Profile> buyersPage = userPage.getContent();

            JSONObject json = new JSONObject();
            json.put("data", buyersPage);
            json.put("totalpages", totalPages);

            return ResponseEntity.status(HttpStatus.OK).body(json);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @ApiOperation(value = "List all the vendors registered on the platform.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all buyers."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/vendors")
    public ResponseEntity<JSONObject> getAllVendors(HttpServletRequest request, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Profile user = profileRepository.findByMail(email);
        Pageable pageAndLimit = PageRequest.of(page, limit);

        if (user.getType() == 2) {
            Page<Profile> userPage =  profileRepository.findAllByTypeEquals(1,pageAndLimit);
            int totalPages = userPage.getTotalPages();
            List<Profile> buyersPage = userPage.getContent();

            JSONObject json = new JSONObject();
            json.put("data", buyersPage);
            json.put("totalpages", totalPages);

            return ResponseEntity.status(HttpStatus.OK).body(json);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
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
    public ResponseEntity<JSONObject> getNumberOfCarsRegisteredByVendor(HttpServletRequest request){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Profile user = profileRepository.findByMail(email);

        if (user.getType() == 2) {
            List<Object> amountOfCarsRegisteredByUser =  profileRepository.findByOwnerCarsRegistered();

            JSONObject json = new JSONObject();
            json.put("data", amountOfCarsRegisteredByUser);

            return ResponseEntity.status(HttpStatus.OK).body(json);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
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
    public ResponseEntity<JSONObject> getNumberOfCarsSoldByVendor(HttpServletRequest request){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Profile user = profileRepository.findByMail(email);

        if (user.getType() == 2) {
            List<Object> amountOfCarsSoldByUser =  profileRepository.findByOwnerCarsSold();

            JSONObject json = new JSONObject();
            json.put("data", amountOfCarsSoldByUser);

            return ResponseEntity.status(HttpStatus.OK).body(json);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }

    @ApiOperation(value = "List the amount of cars vendors have for sale on the platform.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved amount of cars on sale by vendor."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/vendors/cars/selling")
    public ResponseEntity<JSONObject> getNumberOfCarsOnSaleByVendor(HttpServletRequest request){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Profile user = profileRepository.findByMail(email);

        if (user.getType() == 2) {
            List<Object> amountOfCarsOnSaleByUser =  profileRepository.findByOwnerCarsSelling();

            JSONObject json = new JSONObject();
            json.put("data", amountOfCarsOnSaleByUser);

            return ResponseEntity.status(HttpStatus.OK).body(json);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }


}
