package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class HostFileRepositoryTest {

    static final String SEED_PATH = "./data/host-seed.txt";
    static final String TEST_PATH = "./data/host-test.txt";

    HostRepository repository = new HostFileRepository(TEST_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_PATH);
        Path testPath = Paths.get(TEST_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findAll() {
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void add() throws DataException{
        Host test = new Host("Paul", "bob@bob.com", "506", "Test lane", "Test City", "FL", "40098", new BigDecimal(30.30).setScale(2, RoundingMode.HALF_UP), new BigDecimal(40.50).setScale(2, RoundingMode.HALF_UP));
        repository.add(test);
        assertNull(repository.add(null));
        assertEquals(3, repository.findAll().size());
        assertEquals("Paul", repository.findByEmail("bob@bob.com").getLast_name());
    }

    @Test
    void update() throws DataException{
        Host test = new Host("Paul", "bob@bob.com", "506", "Test lane", "Test City", "FL", "40098", new BigDecimal(30.30).setScale(2, RoundingMode.HALF_UP), new BigDecimal(40.50).setScale(2, RoundingMode.HALF_UP));
        test.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertFalse(repository.update(null));
        assertTrue(repository.update(test));
        assertEquals("Paul", repository.findAll().get(0).getLast_name());
    }

    @Test
    void delete() throws DataException{
        Host test = new Host("Paul", "bob@bob.com", "506", "Test lane", "Test City", "FL", "40098", new BigDecimal(30.30).setScale(2, RoundingMode.HALF_UP), new BigDecimal(40.50).setScale(2, RoundingMode.HALF_UP));
        test.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertFalse(repository.delete(null));
        assertTrue(repository.delete(test));
    }

    @Test
    void findByEmail() {
        assertEquals("TX", repository.findByEmail("eyearnes0@sfgate.com").getState());
        assertNull(repository.findByEmail("bob"));
    }
}