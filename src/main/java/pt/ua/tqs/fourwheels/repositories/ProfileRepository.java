package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fourwheels.entities.Profile;

import javax.transaction.Transactional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Integer> {
    Profile findByMail(String email);
    @Transactional
    Profile deleteByMail(String email);

    Page<Profile> findAllByTypeEquals(int type, Pageable pageable);
}
