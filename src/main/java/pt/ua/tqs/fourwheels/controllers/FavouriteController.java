package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Favourite;
import pt.ua.tqs.fourwheels.repositories.FavouriteRepository;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/favourite")
public class FavouriteController {
    private FavouriteRepository favouriteRepository;
    private JwtTokenUtil jwtTokenUtil;

    private JSONObject json = new JSONObject();
    private Logger logger = LogManager.getLogger(ProfileController.class);

    private String auth = "Authorization";
    private String type = "type";
    private String name = "name";
    private String mail = "mail";
    private String contact = "contact";
    private String address = "address";
    private String zipCode = "zipCode";
    private String city = "city";
    private String nif = "nif";
    private String photo = "photo";
    private String error = "error";

    private String bdCred = "Bad Credentials!";


    public FavouriteController(FavouriteRepository favouriteRepository, JwtTokenUtil jwtTokenUtil) {
        this.favouriteRepository = favouriteRepository;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @ApiOperation(value = "Get favourite cars by user.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved car details information."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/")
    //public List<Favourite> getFavourites(HttpServletRequest request){
    public ResponseEntity<JSONObject> getFavourites(HttpServletRequest request){
        String email = "";
        try {
            String token = request.getHeader(auth).split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);
            json.put("data", favouriteRepository.findAllByMail(email));
            return ResponseEntity.status(HttpStatus.OK).body(json);
            //return favouriteRepository.findAllByMail(email);
        }catch (Exception e){
            logger.error(e.toString());
            json.put(error, bdCred);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
    }
    
    @ApiOperation(value = "Delete favourite car by user.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted car favourite."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<JSONObject> deleteFavourite(@PathVariable("id") int id, HttpServletRequest request){
        String email = "";
        try {
            String token = request.getHeader(auth).split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);
        }catch (Exception e){
            logger.error(e.toString());
            json.put(error, bdCred);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
        favouriteRepository.deleteByCarEqualsAndMailEquals(id, email);
        json.put("msg", "Successfully Deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(json);
    }

    @ApiOperation(value = "Save a car in the user favourites list.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully saved a car favourite."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @PostMapping(value = "/{id}")
    public ResponseEntity<JSONObject> addFavourite(@PathVariable("id") int id, HttpServletRequest request){

        String email = "";
        try {
            String token = request.getHeader(auth).split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);
            Favourite newFav = new Favourite();
            newFav.setCar(id);
            newFav.setMail(email);
            json.put("data", newFav);
            favouriteRepository.save(newFav);
            return ResponseEntity.status(HttpStatus.OK).body(json);
            //return favouriteRepository.findAllByMail(email);
        }catch (Exception e){
            logger.error(e.toString());
            json.put(error, bdCred);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
    }
}
