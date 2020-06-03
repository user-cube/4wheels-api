package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fourwheels.entities.Profile;

import javax.transaction.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface ProfileRepository extends CrudRepository<Profile, Integer> {
    Profile findByMail(String email);
    @Transactional
    void deleteByMail(String email);
}
