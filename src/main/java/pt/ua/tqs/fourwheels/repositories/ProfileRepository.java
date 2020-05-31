package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fourwheels.entities.Profile;

import javax.transaction.Transactional;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Integer> {
    Profile findByMail(String email);
    @Transactional
    void deleteByMail(String email);

    List<Profile> findAllByTypeEquals(int type, Pageable pageable);
}
