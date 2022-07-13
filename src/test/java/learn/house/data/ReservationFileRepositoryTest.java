package learn.house.data;

import learn.house.models.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationFileRepositoryTest {

    static final String SEED_FILE_PATH = "./data/reservation_data_test/reservation-seed.csv";
    static final String TEST_FILE_PATH = "./data/reservation_data_test/3f413626-e129-4d06-b68c-36450822213f.csv";
    static final String TEST_DIR_PATH = "./data/reservation_data_test";

    private String hostId = "3f413626-e129-4d06-b68c-36450822213f";

    ReservationRepository repository = new ReservationFileRepository(TEST_DIR_PATH);

    @BeforeEach
    void setup() throws IOException {
        Path seedPath = Paths.get(SEED_FILE_PATH);
        Path testPath = Paths.get(TEST_FILE_PATH);
        Files.copy(seedPath, testPath, StandardCopyOption.REPLACE_EXISTING);
    }

    @Test
    void findByHost() {
        assertEquals(13, repository.findByHost(hostId).size());
    }

    @Test
    void add() throws DataException{
        Reservation reservation = new Reservation(LocalDate.now(), LocalDate.now().plusDays(1), 566, new BigDecimal(300.00).setScale(2, RoundingMode.HALF_UP));
        repository.add(reservation, hostId);
        assertEquals(14, repository.findByHost(hostId).size());
        assertNull(repository.add(null, hostId));
    }

    @Test
    void update() throws DataException{
        Reservation reservation = repository.findByHost(hostId).get(0);
        reservation.setGuest_id(2);
        assertTrue(repository.update(reservation, hostId));
        reservation.setId(99);
        assertFalse(repository.update(reservation, hostId));
    }

    @Test
    void delete() throws DataException{
        Reservation reservation = repository.findByHost(hostId).get(0);
        assertTrue(repository.delete(reservation, hostId));
        assertEquals(12, repository.findByHost(hostId).size());
        Reservation reserve = repository.findByHost(hostId).get(0);
        reserve.setId(999);
        assertFalse(repository.delete(reserve, hostId));
    }
}