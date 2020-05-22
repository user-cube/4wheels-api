package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fourwheels.entities.Favourite;
import pt.ua.tqs.fourwheels.entities.Profile;

@Repository
public interface FavouriteRepository extends CrudRepository<Profile, Integer> {
    Favourite findByMail(String email);
}