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
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UsersController {
    private ProfileRepository profileRepository;
    private JwtTokenUtil jwtTokenUtil;
    private Logger logger = LogManager.getLogger(UsersController.class);
    private JSONObject json = new JSONObject();
    private String auth = "Authorization";
    private String error = "error";
    private String badCredentials = "Bad Credentials";
    private String noAccess = "No Access";
    private String numPages = "totalpages";

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
        json.clear();
        return jsonFrom(null, request, page, limit);
    }

    @ApiOperation(value = "List all the buyers/vendors registered on the platform given the type on the url.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved all buyers/vendors."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @GetMapping(value = "/{type}")
    public ResponseEntity<JSONObject> getAllUsersFromType(@PathVariable("type") int type, HttpServletRequest request, @RequestParam(value = "page", required=false) int page, @RequestParam(value = "limit", required=false) int limit){
        json.clear();
        return jsonFrom(type, request, page, limit);
    }

    public ResponseEntity<JSONObject> jsonFrom(Integer type, HttpServletRequest request, int page, int limit) {
        try {
            String token = request.getHeader(auth).split(" ")[1];
            String email = jwtTokenUtil.getUsernameFromToken(token);
            Profile user = profileRepository.findByMail(email);
            Pageable pageAndLimit = PageRequest.of(page, limit);
            Page<Profile> userPage;

            if (user.getType() == 2) {
                if (type != null) {
                    userPage = profileRepository.findAllByTypeEquals(type, pageAndLimit);
                } else {
                    userPage = profileRepository.findAll(pageAndLimit);
                }
                int totalPages = userPage.getTotalPages();
                List<Profile> userOfPage = userPage.getContent();
                json.put("data", userOfPage);
                json.put(numPages, totalPages);
                return ResponseEntity.status(HttpStatus.OK).body(json);
            } else {
                return errorAccess();
            }
        } catch (Exception e) {
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

}
