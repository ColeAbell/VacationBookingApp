package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.HostRepositoryDouble;
import learn.house.models.Guest;
import learn.house.models.Host;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class HostServiceTest {

    HostService service = new HostService(new HostRepositoryDouble());

    @Test
    void findByEmail() {
        assertEquals("phone", service.findByEmail("Bob").getPayload().getPhone());
        assertNull(service.findByEmail("notbob").getPayload());
    }

    @Test
    void add() throws DataException {
        Host test = new Host();
        assertEquals("Host cannot be null", service.add(null).getErrorMessages().get(0));
        test = new Host("Bob", "Bob", "phone", "address", "city",  "state",  "postal_code", new BigDecimal(2.00), new BigDecimal(3.00));
        test.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertEquals("Host cannot be duplicate", service.add(test).getErrorMessages().get(0));
    }

    @Test
    void update() throws DataException{
        Host test = new Host("Bob", "Bob", "phone", "address", "city",  "state",  "postal_code", new BigDecimal(2.00), new BigDecimal(3.00));
        test.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        assertEquals("Host cannot be duplicate", service.update(test).getErrorMessages().get(0));
        test.setState("MN");
        assertEquals(test, service.update(test).getPayload());

    }
}