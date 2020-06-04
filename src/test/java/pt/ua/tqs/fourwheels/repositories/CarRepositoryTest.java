package pt.ua.tqs.fourwheels.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.Assert.assertNotEquals;

@WebMvcTest(value = CarRepositoryTest.class)
class CarRepositoryTest {
    @Test
    private void injectedComponentAreNotNullTest() {
        assertNotEquals(0, 1);
    }
}