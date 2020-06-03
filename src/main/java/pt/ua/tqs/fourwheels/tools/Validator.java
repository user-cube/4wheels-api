package pt.ua.tqs.fourwheels.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import pt.ua.tqs.fourwheels.authentication.JwtTokenUtil;
import pt.ua.tqs.fourwheels.repositories.Authentication;

@Service
public class Validator {
    public JwtTokenUtil jwtTokenUtil;
    public Authentication authentication;
    private Logger logger = LogManager.getLogger(Validator.class);


    public Validator(JwtTokenUtil jwtTokenUtil, Authentication authentication) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.authentication = authentication;
    }

    public String tokenValidator(String token){
        try {
            return jwtTokenUtil.getUsernameFromToken(token); // Get token from email
        } catch (Exception e){
            logger.error(e.toString());
            return "";
        }
    }
}
