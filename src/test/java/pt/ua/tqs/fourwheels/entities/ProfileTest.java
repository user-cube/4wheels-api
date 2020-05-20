package pt.ua.tqs.fourwheels.entities;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ProfileTest {
    Profile set;
    Profile get;
    @Before
    void setUp(){
        Profile set = new Profile();
        Profile get = new Profile();
        get.setType("Vendedor");
        get.setCidade("Porto");
        get.setCodigPostal(555);
        get.setContacto(999999999);
        get.setMail("lmao@mail.com");
        get.setMorada("Rua perfeita");
        get.setName("EUSOU Lesias");
        get.setNumeroContribuinte(9412);
    }
    @Test
    void getId() {
        assertThat(get.getId() >= 0).isEqualTo(true);
    }

    @Test
    void getType() {
        assertThat(get.getType()).isEqualTo("Vendedor");
    }

    @Test
    void getName() {
        assertThat(get.getName()).isEqualTo("EUSOU Lesias");
    }

    @Test
    void getMail() {
        assertThat(get.getMail()).isEqualTo("lmao@mail.com");
    }

    @Test
    void getContacto() {
        assertThat(get.getContacto()).isEqualTo(999999999);
    }

    @Test
    void getMorada() {
        assertThat(get.getMorada()).isEqualTo("Rua perfeita");
    }

    @Test
    void getCodigPostal() {
        assertThat(get.getCodigPostal()).isEqualTo(555);
    }

    @Test
    void getCidade() {
        assertThat(get.getType()).isEqualTo("Porto");
    }

    @Test
    void getNumeroContribuinte() {
        assertThat(get.getType()).isEqualTo(9412);
    }

    @Test
    void setType() {
        set.setType("Vendedor");
        assertThat(set.getType()).isEqualTo("Vendedor");
    }

    @Test
    void setName() {
        set.setName("DummyTest");
        assertThat(set.getName()).isEqualTo("DummyTest");
    }

    @Test
    void setMail() {
        set.setMail("dummy@Test.This");
        assertThat(set.getMail()).isEqualTo("dummy@Test.This");
    }

    @Test
    void setContacto() {
        set.setContacto(913913913);
        assertThat(set.getContacto()).isEqualTo(913913913);
    }

    @Test
    void setMorada() {
        set.setMorada("Rua Falsa");
        assertThat(set.getMorada()).isEqualTo("Rua Falsa");
    }

    @Test
    void setCodigPostal() {
        set.setCodigPostal(678798);
        assertThat(set.getCodigPostal()).isEqualTo(678798);
    }

    @Test
    void setCidade() {
        set.setCidade("Nowhere");
        assertThat(set.getCidade()).isEqualTo("Nowhere");
    }

    @Test
    void setNumeroContribuinte() {
        set.setNumeroContribuinte(555);
        assertThat(set.getNumeroContribuinte()).isEqualTo(555);
    }
}