package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;

import java.util.Optional;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private ProfileRepository profileRepository;

    public ProfileController(ProfileRepository profileRepository){
        this.profileRepository = profileRepository;
    }

    @ApiOperation(value = "Get profile info for a specific user.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user profile information."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/{id}")
    public @ResponseBody Optional<Profile> getInfo(@PathVariable("id") int id){
        return profileRepository.findById(id);
    }


    @ApiOperation(value = "Insert a profile on the database.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully inserted user profile information."),
            @ApiResponse(code = 401, message = "You are not authorized to insert the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @PostMapping(value = "/")
    public Profile insertProfile(@RequestBody Profile user){
        return profileRepository.save(user);
    }

}