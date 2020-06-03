package pt.ua.tqs.fourwheels.services;

import java.util.ArrayList;

import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.ua.tqs.fourwheels.models.UserModel;
import pt.ua.tqs.fourwheels.repositories.Authentication;
import pt.ua.tqs.fourwheels.entities.Userm;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Service
public class JwtUserDetailsService implements UserDetailsService {


    private Authentication authentication;

    private PasswordEncoder bcryptEncoder;

    public JwtUserDetailsService(Authentication authentication, PasswordEncoder bcryptEncoder) {
        this.authentication = authentication;
        this.bcryptEncoder = bcryptEncoder;
    }

    private Logger logger = LogManager.getLogger(JwtUserDetailsService.class.getName());
    private static final Marker USER_MARKER = MarkerManager.getMarker("USER");
    @Override
    public UserDetails loadUserByUsername(String username){
        Userm user = authentication.findByUsername(username);
        if (user == null) {
            logger.info(USER_MARKER , "User not found with username: {}", username);
            return null;
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public Userm save(UserModel user) {
        Userm newUser = new Userm();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        return authentication.save(newUser);
    }
}