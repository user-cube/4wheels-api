package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.repository.CrudRepository;
import pt.ua.tqs.fourwheels.entities.Profile;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ProfileRepository extends CrudRepository<Profile, Integer> {

}
