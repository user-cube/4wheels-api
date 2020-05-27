package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fourwheels.entities.Favourite;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FavouriteRepository extends CrudRepository<Favourite, Integer> {
    List<Favourite> findByMail(String email);
    @Transactional
    void deleteByCarEqualsAndMailEquals(int id, String email);

}