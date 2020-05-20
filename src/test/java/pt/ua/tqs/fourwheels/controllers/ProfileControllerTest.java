package pt.ua.tqs.fourwheels.controllers;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pt.ua.tqs.fourwheels.entities.Profile;
import pt.ua.tqs.fourwheels.repositories.ProfileRepository;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {

        @Mock(lenient = true)
        private ProfileRepository repository;

        private int userID = 1;
        @Before
        void setUp(){
            Profile dummy = new Profile();
            dummy.setType("Vendedor");
            dummy.setCidade("Porto");
            dummy.setCodigPostal(555);
            dummy.setContacto(999999999);
            dummy.setMail("lmao@mail.com");
            dummy.setMorada("Rua perfeita");
            dummy.setName("EUSOU Lesias");
            dummy.setNumeroContribuinte(9412);
            userID = dummy.getId();
            when(repository.findById(dummy.getId())).thenReturn(java.util.Optional.of(dummy));
        }
        @Test
        void getInfo() {
            Profile userProfile = repository.findById(userID).get();
            assertThat(userProfile.getName()).isEqualTo("EUSOU Lesias");
        }
}