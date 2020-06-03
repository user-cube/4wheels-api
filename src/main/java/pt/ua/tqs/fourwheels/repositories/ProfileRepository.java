package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fourwheels.entities.Car;
import pt.ua.tqs.fourwheels.entities.Profile;

import javax.persistence.Tuple;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface ProfileRepository extends PagingAndSortingRepository<Profile, Integer> {
    Profile findByMail(String email);
    @Transactional
    void deleteByMail(String email);
    Page<Profile> findAllByTypeEquals(int type, Pageable pageable);

    @Query(value="select profile.name, count(owner_mail) from profile join car c on profile.mail = c.owner_mail group by owner_mail", nativeQuery=true)
    List<Object> findByOwnerCarsRegistered();
}
