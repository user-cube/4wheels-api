package pt.ua.tqs.fourwheels.repositories;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import pt.ua.tqs.fourwheels.entities.Profile;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProfileRepositoryTest {
    private ProfileRepository repository;
    private Profile dummy;
    @Before
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
        assertThat(prf.getName()).isEqualTo(dummy.getName());
        assertThat(prf.getType()).isEqualTo(dummy.getType());
        assertThat(prf.getCidade()).isEqualTo(dummy.getCidade());
        assertThat(prf.getCodigPostal()).isEqualTo(dummy.getCodigPostal());
        assertThat(prf.getContacto()).isEqualTo(dummy.getContacto());
        assertThat(prf.getMail()).isEqualTo(dummy.getMail());
        assertThat(prf.getMorada()).isEqualTo(dummy.getMorada());
        assertThat(prf.getNumeroContribuinte()).isEqualTo(dummy.getNumeroContribuinte());
    }
    @Test
    void checkIfExistsRepository(){
        repository.save(dummy);
        assertThat(repository.equals(dummy)).isTrue();
        assertThat(repository.existsById(dummy.getId())).isTrue();
    }
    @Test
    void checkIfExistsRepository(){
        repository.save(dummy);
        repository.deleteById(dummy.getId());
        assertThat(repository.existsById(dummy.getId())).isFalse();
    }

}