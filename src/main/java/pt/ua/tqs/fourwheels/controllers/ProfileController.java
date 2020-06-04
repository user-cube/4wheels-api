package pt.ua.tqs.fourwheels.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.json.simple.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.models.ProfileModel;
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

    private JSONObject json = new JSONObject();
    private JwtTokenUtil jwtTokenUtil;
    private ProfileRepository profileRepository;
    private Logger logger = LogManager.getLogger(ProfileController.class);


    public ProfileController(ProfileRepository profileRepository, JwtTokenUtil jwtTokenUtil){
        this.profileRepository = profileRepository;
        this.jwtTokenUtil = jwtTokenUtil;
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
    public ResponseEntity<JSONObject> getInfo(HttpServletRequest request){
        try {
            String token = request.getHeader("Authorization").split(" ")[1]; // Get token from header
            String email = jwtTokenUtil.getUsernameFromToken(token);

            Profile p = profileRepository.findByMail(email);
            json.put("id", p.getId());
            json.put("type", p.getType());
            json.put("name", p.getName());
            json.put("mail", email);
            // json.put("mail", optionalProf.getMail());
            json.put("contact", p.getContact());
            json.put("address", p.getAddress());
            json.put("zipCode", p.getZipCode());
            json.put("city", p.getCity());
            json.put("nif", p.getNif());
            json.put("photo", p.getPhoto());

            //return ResponseEntity.ok(profileRepository.findByMail(email));
            return ResponseEntity.status(HttpStatus.OK).body(json);
        } catch (Exception e){
            logger.error(e.toString());
            json.put("error", "Bad credentials");
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
    public ResponseEntity<JSONObject> insertProfile(@RequestBody ProfileModel newProfile, HttpServletRequest request){
        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            System.out.println(token);
            email = jwtTokenUtil.getUsernameFromToken(token);
        }catch (Exception e){
            logger.error(e.toString());
            json.put("error", "Bad credentials");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }

        profileRepository.save(newProfile.getProfile());

        json.put("id", newProfile.getProfile().getId());
        json.put("type", newProfile.getProfile().getType());
        json.put("name", newProfile.getProfile().getName());
        json.put("mail", email);
        // json.put("mail", optionalProf.getMail());
        json.put("contact", newProfile.getProfile().getContact());
        json.put("address", newProfile.getProfile().getAddress());
        json.put("zipCode", newProfile.getProfile().getZipCode());
        json.put("city", newProfile.getProfile().getCity());
        json.put("nif", newProfile.getProfile().getNif());
        json.put("photo", newProfile.getProfile().getPhoto());

        return ResponseEntity.status(HttpStatus.OK).body(json);
        //return ResponseEntity.ok(profileRepository.save(profile));
        //return ResponseEntity.status(HttpStatus.OK).body(profileRepository.save(profile));
        //return ResponseEntity.status(HttpStatus.OK).body(profileRepository.save(newProfile.getProfile()));
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
    public ResponseEntity<JSONObject> deleteProfile(HttpServletRequest request){
        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);
        }catch (Exception e){
            logger.error(e.toString());
            json.put("error", "Bad credentials");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
        profileRepository.deleteByMail(email);
        json.put("msg", "Successfully Deleted!");
        return ResponseEntity.status(HttpStatus.OK).body(json);
        //return ResponseEntity.ok(profileRepository.deleteByMail(email));
    }


    @ApiOperation(value = "Edit profile details for a specific user.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully edited profile information."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @PutMapping(value = "/")
    public ResponseEntity<JSONObject> editProfileInfo(@RequestBody ProfileModel newProfile, HttpServletRequest request){
        String email = "";
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            email = jwtTokenUtil.getUsernameFromToken(token);

            Profile optionalProf = profileRepository.findByMail(email);
            optionalProf.setPhoto(newProfile.getProfile().getPhoto());
            optionalProf.setName(newProfile.getProfile().getName());
            optionalProf.setContact(newProfile.getProfile().getContact());
            optionalProf.setAddress(newProfile.getProfile().getAddress());
            optionalProf.setZipCode(newProfile.getProfile().getZipCode());
            optionalProf.setCity(newProfile.getProfile().getCity());
            optionalProf.setNif(newProfile.getProfile().getNif());

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
            System.out.println("AQUI EXCE" + e.toString());
            logger.error(e.toString());
            json.put("error", "Bad credentials");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(json);
        }
    }
}