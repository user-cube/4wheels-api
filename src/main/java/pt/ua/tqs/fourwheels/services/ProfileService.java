package pt.ua.tqs.fourwheels.services;

import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;

import org.apache.logging.log4j.Logger;
import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProfileService {
    private Logger logger = LogManager.getLogger(ProfileService.class);
    @Autowired
    private ProfileRepository userRepository;

    @Transactional
    public void addUser(Profile user){
        userRepository.save(user);
    }

    @Transactional
    public Profile getUserById(int id){
        try {
            Optional<Profile> pf = userRepository.findById(id);
            return pf.get();
        }catch(Exception e) {
            logger.error(e.toString());
            Profile nullPf = new Profile();
            nullPf.setName("null");
            return nullPf;
        }
    }
}