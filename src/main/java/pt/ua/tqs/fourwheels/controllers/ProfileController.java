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
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.Authentication;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;
import pt.ua.tqs.fourwheels.tools.Validator;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private ProfileRepository profileRepository;
    private JwtTokenUtil jwtTokenUtil;
    private Authentication authentication;
    private JSONObject json = new JSONObject();
    private Validator validator;
    private Logger logger = LogManager.getLogger(ProfileController.class);


    public ProfileController(ProfileRepository profileRepository, JwtTokenUtil jwtTokenUtil, Authentication authentication, Validator validator){
        this.profileRepository = profileRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authentication = authentication;
        this.validator = new Validator(jwtTokenUtil, authentication);
    }

    /**
     * Get profile information from user
     * given a token in authorization
     * header.
     *
     * @param request headers
     * @return a list with all user information
     * otherwise return HTTP STATUS 403.
     */
    @ApiOperation(value = "Get profile info for a specific user.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved user profile information."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/")
    public ResponseEntity getInfo(HttpServletRequest request){
        json.put("error", "Bad credentials");
        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1]; // Get token from header
            email = validator.tokenValidator(token);
        } catch (Exception e){
            logger.error(e.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
        if (!email.equals("")) return ResponseEntity.ok(profileRepository.findByMail(email));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
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
    public ResponseEntity insertProfile(@RequestBody Profile user, HttpServletRequest request){
        json.put("error", "Bad credentials");
        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1]; // Get token from header
            email = validator.tokenValidator(token);
        } catch (Exception e){
            logger.error(e.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
        if (!email.equals("")) return ResponseEntity.ok(profileRepository.save(user));
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
    }

    @ApiOperation(value = "Delete a profile from the database.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully delete profile."),
            @ApiResponse(code = 401, message = "You are not authorized to insert the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @DeleteMapping(value = "/")
    public void deleteProfile(HttpServletRequest request){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        profileRepository.deleteByMail(email);
    }

    /**
     * Edit Specific profile
     * @param id
     * @return
     */
    @ApiOperation(value = "Edit profile details for a specific user.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited profile information."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @PutMapping(value = "/")
    public ResponseEntity<Profile> editProfileInfo(@RequestBody Profile newProfile, HttpServletRequest request){
        String token = request.getHeader("Authorization").split(" ")[1];
        String email = jwtTokenUtil.getUsernameFromToken(token);
        Profile optionalProf = profileRepository.findByMail(email);
        optionalProf.setPhoto(newProfile.getPhoto());
        optionalProf.setName(newProfile.getName());
        optionalProf.setContact(newProfile.getContact());
        optionalProf.setAddress(newProfile.getAddress());
        optionalProf.setZipCode(newProfile.getZipCode());
        optionalProf.setCity(newProfile.getCity());
        optionalProf.setNif(newProfile.getNif());
        return ResponseEntity.ok(profileRepository.save(optionalProf));
    }

}