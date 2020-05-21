package pt.ua.tqs.fourwheels.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pt.ua.tqs.fourwheels.models.UserModel;
import pt.ua.tqs.fourwheels.repositories.Authentication;
import pt.ua.tqs.fourwheels.entities.Userm;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private Authentication authentication;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    Logger logger = LogManager.getLogger(JwtUserDetailsService.class);
    @Override
    public UserDetails loadUserByUsername(String username){
        Userm user = authentication.findByUsername(username);
        if (user == null) {
            logger.info("User not found with username: " + username);
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