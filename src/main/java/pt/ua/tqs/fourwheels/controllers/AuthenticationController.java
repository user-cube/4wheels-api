package pt.ua.tqs.fourwheels.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pt.ua.tqs.fourwheels.entities.Userm;
import pt.ua.tqs.fourwheels.models.UserModel;
import pt.ua.tqs.fourwheels.services.JwtUserDetailsService;
import pt.ua.tqs.fourwheels.authentication.JwtRequest;
import pt.ua.tqs.fourwheels.authentication.JwtResponse;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;

@RestController
public class AuthenticationController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){

        final UserDetails userDetails = userDetailsService
               .loadUserByUsername(authenticationRequest.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        if (token == null){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Userm> saveUser(@RequestBody UserModel user){
        return ResponseEntity.ok(userDetailsService.save(user));
    }
}

