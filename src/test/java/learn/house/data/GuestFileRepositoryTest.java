package learn.house.data;

import learn.house.models.Guest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class GuestFileRepositoryTest {

    static final String SEED_PATH = "./data/guest-seed.txt";
    static final String TEST_PATH = "./data/guest-test.txt";

    GuestRepository repository = new GuestFileRepository(TEST_PATH);

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
    void findByEmail() {
        assertEquals(1, repository.findByEmail("slomas0@mediafire.com").getGuest_id());
        assertNull(repository.findByEmail("bob"));
    }

    @Test
    void add() throws DataException{
        Guest test = new Guest(0, "Bob", "The first", "bob@bob.com", "506", "FL");
        repository.add(test);
        assertNull(repository.add(null));
        assertEquals(3, repository.findAll().size());
        assertEquals(3, repository.findByEmail("bob@bob.com").getGuest_id());

    }

    @Test
    void update() throws DataException{
        Guest test = new Guest(0,"Bob", "The first", "bob@bob.com", "506", "FL");
        test.setGuest_id(1);
        assertFalse(repository.update(null));
        assertTrue(repository.update(test));
        assertEquals("Bob", repository.findAll().get(0).getFirst_name());

    }

    @Test
    void delete() throws DataException{
        Guest test = repository.findAll().get(0);
        assertEquals(true, repository.delete(test));
        Guest testTwo = new Guest(0,"Bob", "The first", "bob@bob.com", "506", "FL");
        assertEquals(false, repository.delete(testTwo));
    }
}