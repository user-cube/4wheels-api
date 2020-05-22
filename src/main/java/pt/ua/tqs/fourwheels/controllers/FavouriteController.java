package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Favourite;
import pt.ua.tqs.fourwheels.repositories.FavouriteRepository;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/favourite")
public class FavouriteController {
    private FavouriteRepository favouriteRepository;
    private JwtTokenUtil jwtTokenUtil;

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
    public Favourite getFavourites(HttpServletRequest request){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        return favouriteRepository.findByMail(email);
    }

    @ApiOperation(value = "Get favourite cars by user.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved car details information."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @DeleteMapping(value = "/{id}")
    public void deleteFavourite(@PathVariable("id") int id, HttpServletRequest request){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        System.out.println(email);
        favouriteRepository.deleteByCarEqualsAndMailEquals(id, email);
    }

}
