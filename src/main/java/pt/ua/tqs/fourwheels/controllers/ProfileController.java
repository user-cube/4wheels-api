package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.Authentication;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;

import javax.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private Authentication authentication;
    private JSONObject json = new JSONObject();
    private JwtTokenUtil jwtTokenUtil;
    private ProfileRepository profileRepository;
    private Logger logger = LogManager.getLogger(ProfileController.class);


    public ProfileController(ProfileRepository profileRepository, JwtTokenUtil jwtTokenUtil, Authentication authentication){
        this.profileRepository = profileRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authentication = authentication;
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
        try {
            String token = request.getHeader("Authorization").split(" ")[1]; // Get token from header
            String email = jwtTokenUtil.getUsernameFromToken(token);
            return ResponseEntity.ok(profileRepository.findByMail(email));
        } catch (Exception e){
            logger.error(e.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
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
    // public Profile insertProfile(@RequestBody Profile user){
    public ResponseEntity insertProfile(@RequestBody Profile newProfile, HttpServletRequest request){
        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            System.out.println(token);
            email = jwtTokenUtil.getUsernameFromToken(token);
        }catch (Exception e){
            logger.error(e.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }

        Profile profile = new Profile(
                newProfile.getId(),
                newProfile.getType(),
                newProfile.getName(),
                newProfile.getMail(),
                newProfile.getContact(),
                newProfile.getAddress(),
                newProfile.getZipCode(),
                newProfile.getCity(),
                newProfile.getNif(),
                newProfile.getPhoto());

        //return ResponseEntity.ok(profileRepository.save(profile));
        return ResponseEntity.status(HttpStatus.OK).body(profileRepository.save(profile));
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
    public ResponseEntity deleteProfile(HttpServletRequest request){
        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);
        }catch (Exception e){
            logger.error(e.toString());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
        return ResponseEntity.ok(profileRepository.deleteByMail(email));
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
    public ResponseEntity editProfileInfo(@RequestBody Profile newProfile, HttpServletRequest request){
        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);

            Profile optionalProf = profileRepository.findByMail(email);
            optionalProf.setPhoto(newProfile.getPhoto());
            optionalProf.setName(newProfile.getName());
            optionalProf.setContact(newProfile.getContact());
            optionalProf.setAddress(newProfile.getAddress());
            optionalProf.setZipCode(newProfile.getZipCode());
            optionalProf.setCity(newProfile.getCity());
            optionalProf.setNif(newProfile.getNif());

            profileRepository.save(optionalProf);

            json.put("id", optionalProf.getId());
            json.put("type", optionalProf.getType());
            json.put("name", optionalProf.getName());
            json.put("mail", email);
            // json.put("mail", optionalProf.getMail());
            json.put("contact", optionalProf.getContact());
            json.put("address", optionalProf.getAddress());
            json.put("zipCode", optionalProf.getZipCode());
            json.put("city", optionalProf.getCity());
            json.put("nif", optionalProf.getNif());
            json.put("photo", optionalProf.getPhoto());
            //return ResponseEntity.ok(profileRepository.save(optionalProf));
            //return ResponseEntity.status(HttpStatus.OK).body(profileRepository.save(optionalProf).toString());
            return ResponseEntity.status(HttpStatus.OK).body(json);

        }catch (Exception e){
                logger.error(e.toString());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
    }
}