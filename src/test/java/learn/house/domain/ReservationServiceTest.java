package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.ReservationRepositoryDouble;
import learn.house.models.Reservation;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    ReservationService service = new ReservationService(new ReservationRepositoryDouble());

    @Test
    void checkForDoubleBook() {
        assertEquals(1, service.checkForDoubleBook("id", LocalDate.now().minusDays(1), LocalDate.now()).size());
        assertEquals(1, service.checkForDoubleBook("hi", LocalDate.now().plusDays(2), LocalDate.now().plusDays(4)).size());
        assertEquals(1, service.checkForDoubleBook("Hi", LocalDate.now().plusDays(1), LocalDate.now().plusWeeks(1)).size());
        assertEquals(0, service.checkForDoubleBook("hi", LocalDate.now().plusWeeks(4), LocalDate.now().plusWeeks(5)).size());
    }

    @Test
    void add() throws DataException {
        Reservation reservation = new Reservation(LocalDate.now(), LocalDate.now().plusDays(6), 30, new BigDecimal(200.99));
        assertEquals(reservation, service.add(reservation, "j").getPayload());
        assertNull(service.add(null, "hi").getPayload());
    }

    @Test
    void update() throws DataException{
        Reservation reservation = new Reservation(LocalDate.now(), LocalDate.now().plusDays(2), 30, new BigDecimal(300.00));
        assertEquals("Reservation cannot be duplicate", service.update(reservation, "j").getErrorMessages().get(0));
        reservation.setEnd_date(LocalDate.now().plusWeeks(19));
        reservation.setId(1);
        assertEquals(reservation, service.update(reservation, "l").getPayload());
        reservation.setId(0);
        assertNull(service.update(reservation, "k").getPayload());
    }

    @Test
    void delete() throws DataException{
        Reservation reservation = new Reservation(LocalDate.now(), LocalDate.now().plusDays(2), 30, new BigDecimal(300.00));
        reservation.setId(1);
        assertEquals(reservation, service.delete(reservation, "k").getPayload());
        reservation.setId(0);
        assertEquals("Reservation to be deleted does not exist", service.delete(reservation, "l").getErrorMessages().get(0));
    }
}