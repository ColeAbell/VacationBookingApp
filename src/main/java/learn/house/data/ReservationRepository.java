package learn.house.data;

import learn.house.models.Host;
import learn.house.models.Reservation;

import java.util.List;

public interface ReservationRepository {

    Reservation add(Reservation reservation, String hostId) throws DataException;

    List<Reservation> findByHost(String hostId);

    boolean update(Reservation reservation, String hostId) throws DataException;

    boolean delete(Reservation reservation, String hostId) throws DataException;
}
