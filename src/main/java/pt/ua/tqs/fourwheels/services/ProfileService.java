package pt.ua.tqs.fourwheels.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository userRepository;

    @Transactional
    public void addUser(Profile user){
        userRepository.save(user);
    }

    @Transactional
    public Profile getUserById(int id){
        Optional<Profile> profile = userRepository.findById(id);
        return profile.orElse(null);

    }
}