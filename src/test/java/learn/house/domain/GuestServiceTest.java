package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepositoryDouble;
import learn.house.models.Guest;
import learn.house.models.Reservation;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GuestServiceTest {

    GuestService service = new GuestService(new GuestRepositoryDouble());

    @Test
    void findByEmail() {
        assertEquals("Test", service.findByEmail("test@test.com").getPayload().getFirst_name());
        assertNull(service.findByEmail("hi").getPayload());
    }

    @Test
    void add() throws DataException {
        Guest test = new Guest();
        assertEquals("Guest cannot be null", service.add(null).getErrorMessages().get(0));
        assertEquals("First name is required", service.add(test).getErrorMessages().get(0));
        test.setFirst_name("Bob");
        assertEquals("Last name is required", service.add(test).getErrorMessages().get(0));
        test.setLast_name("Bob");
        assertEquals("Email is required", service.add(test).getErrorMessages().get(0));
        test.setEmail("Bob");
        assertEquals("Phone number is required", service.add(test).getErrorMessages().get(0));
        test.setPhone("bob");
        assertEquals("State is required", service.add(test).getErrorMessages().get(0));
        test.setState("bb");
        assertTrue(service.add(test).isSuccess());
        Guest theGuest = new Guest(1, "Test", "Testington", "test@test.com", "8767804356", "TX");
        assertEquals("Guest cannot be duplicate", service.add(theGuest).getErrorMessages().get(0));

    }

    @Test
    void update() throws DataException{
        Guest theGuest = new Guest(1, "Test", "Testington", "test@test.com", "8767804356", "TX");
        assertEquals("Guest cannot be duplicate", service.update(theGuest).getErrorMessages().get(0));
        theGuest.setEmail("hello");
        assertEquals(theGuest, service.update(theGuest).getPayload());
    }

}