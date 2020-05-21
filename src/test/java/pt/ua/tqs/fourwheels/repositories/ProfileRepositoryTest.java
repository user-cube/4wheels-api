package pt.ua.tqs.fourwheels.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import pt.ua.tqs.fourwheels.entities.Profile;

import static org.junit.Assert.*;


@WebMvcTest(value = ProfileRepositoryTest.class)
class ProfileRepositoryTest {
    @Autowired
    private ProfileRepository userRepository;
    @Test
    void injectedComponentAreNotNullTest() {
        assertNotEquals(null, userRepository);
    }
    /*
    private Profile dummy;
    @BeforeEach
    void setUp() {
        dummy = new Profile();
        dummy.setId(2);
        dummy.setType("Vendedor");
        dummy.setCidade("Porto");
        dummy.setCodigPostal(555);
        dummy.setContacto(999999999);
        dummy.setMail("lmao@mail.com");
        dummy.setMorada("Rua perfeita");
        dummy.setName("EUSOU Lesias");
        dummy.setNumeroContribuinte(9412);
        System.out.println(userRepository.existsById(2));
        userRepository.save(dummy);
        System.out.println(userRepository.findById(2));
    }
    @AfterEach
    void tierDown() {
        userRepository.deleteById(dummy.getId());
    }
    @Test
    void addToRepository(){
        Profile prf = userRepository.findById(dummy.getId()).get();
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
        assertEquals(userRepository.equals(dummy), true);
        assertEquals(userRepository.existsById(dummy.getId()), true);
    }
    @Test
    void checkIfDeletedRepository(){
        assertEquals(userRepository.existsById(dummy.getId()), true);
        userRepository.deleteById(dummy.getId());
        assertEquals(userRepository.existsById(dummy.getId()), false);
        userRepository.save(dummy);
    }*/
}