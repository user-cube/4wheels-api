package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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
    Page<Profile> findAllByTypeEquals(int type, Pageable pageable);

    @Query(value="select profile.name, count(owner_mail) as counter from profile join car c on profile.mail = c.owner_mail group by owner_mail order by counter desc", nativeQuery=true)
    List<Object> findByOwnerCarsRegistered();

    @Query(value="select profile.name, count(owner_mail) as counter from profile join car c on profile.mail = c.owner_mail group by owner_mail order by counter desc limit ?1", nativeQuery=true)
    List<Object> findByOwnerCarsRegisteredLimited(int limit);

    @Query(value="select name, count(owner_mail) as counter from profile join car c on profile.mail = c.owner_mail where car_state='sold' group by owner_mail order by counter desc", nativeQuery=true)
    List<Object> findByOwnerCarsSold();

    @Query(value="select name, count(owner_mail) as counter from profile join car c on profile.mail = c.owner_mail where car_state='sold' group by owner_mail order by counter desc limit ?1", nativeQuery=true)
    List<Object> findByOwnerCarsSoldLimited(int limit);

    @Query(value="select name, count(owner_mail) as counter from profile join car c on profile.mail = c.owner_mail where car_state='selling' group by owner_mail order by counter desc", nativeQuery=true)
    List<Object> findByOwnerCarsSelling();

    @Query(value="select name, count(owner_mail) as counter from profile join car c on profile.mail = c.owner_mail where car_state='selling' group by owner_mail order by counter desc limit ?1", nativeQuery=true)
    List<Object> findByOwnerCarsSellingLimited(int limit);

    int countAllByTypeEquals(int type);
}
