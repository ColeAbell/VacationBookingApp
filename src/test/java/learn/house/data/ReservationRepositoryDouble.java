package learn.house.data;

import learn.house.models.Reservation;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationRepositoryDouble implements ReservationRepository{

    public final static Reservation reservation = new Reservation(LocalDate.now(), LocalDate.now().plusDays(2), 30, new BigDecimal(300.00));

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    public ReservationRepositoryDouble(){
        reservation.setId(1);
        reservations.add(reservation);
    }

    @Override
    public List<Reservation> findByHost(String hostId){
        return reservations;
    }

    @Override
    public Reservation add(Reservation reservation, String hostId){
        if(reservation == null){
            return null;
        }
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation, String hostId) throws DataException {
        List<Reservation> all = findByHost(hostId);
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == reservation.getId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation, String hostId) throws DataException {
        List<Reservation> all = findByHost(hostId);
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == reservation.getId()) {
                return true;
            }
        }
        return false;
    }
}
