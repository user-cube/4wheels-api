package pt.ua.tqs.fourwheels.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pt.ua.tqs.fourwheels.entities.Userm;

@Repository
public interface Authentication extends CrudRepository<Userm, Integer> {

    Userm findByUsername(String username);
}