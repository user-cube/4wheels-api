package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.entities.Profile;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {
    Profile findByMail(String email);

}
