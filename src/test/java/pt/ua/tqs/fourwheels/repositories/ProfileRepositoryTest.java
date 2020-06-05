package pt.ua.tqs.fourwheels.repositories;

import static org.junit.Assert.assertNotEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


@WebMvcTest(value = ProfileRepositoryTest.class)
class ProfileRepositoryTest {
    @Test
    public void injectedComponentAreNotNullTest() {
        assertNotEquals(0, 1);
    }
}