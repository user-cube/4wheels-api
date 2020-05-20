package pt.ua.tqs.fourwheels.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ua.tqs.fourwheels.entities.Profile;

import static org.junit.Assert.*;


@SpringBootTest
class ProfileRepositoryTest {
    private ProfileRepository repository;
    private Profile dummy;
    @BeforeEach
    void setUp() {
        dummy = new Profile();
        dummy.setType("Vendedor");
        dummy.setCidade("Porto");
        dummy.setCodigPostal(555);
        dummy.setContacto(999999999);
        dummy.setMail("lmao@mail.com");
        dummy.setMorada("Rua perfeita");
        dummy.setName("EUSOU Lesias");
        dummy.setNumeroContribuinte(9412);
    }
    @Test
    void addToRepository(){
        repository.save(dummy);
        Profile prf = repository.findById(dummy.getId()).get();
        assertEquals(prf.getName(), dummy.getName());
        assertEquals(prf.getType(), dummy.getType());
        assertEquals(prf.getCidade(), dummy.getCidade());
        assertEquals(prf.getCodigPostal(), dummy.getCodigPostal());
        assertEquals(prf.getContacto(), dummy.getContacto());
        assertEquals(prf.getMail(), dummy.getMail());
        assertEquals(prf.getMorada(), dummy.getMorada());
        assertEquals(prf.getNumeroContribuinte(), dummy.getNumeroContribuinte());
    }
    @Test
    void checkIfExistsRepository(){
        repository.save(dummy);
        assertEquals(repository.equals(dummy), true);
        assertEquals(repository.existsById(dummy.getId()), true);
    }
    @Test
    void checkIfDeletedRepository(){
        repository.save(dummy);
        assertEquals(repository.existsById(dummy.getId()), true);
        repository.deleteById(dummy.getId());
        assertEquals(repository.existsById(dummy.getId()), false);
    }

}