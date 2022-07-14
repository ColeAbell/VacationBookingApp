package learn.house.data;

import learn.house.models.Guest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GuestRepositoryDouble implements GuestRepository{

    public final static Guest theGuest = new Guest(1, "Test", "Testington", "test@test.com", "8767804356", "TX");
    private final ArrayList<Guest> guests = new ArrayList<>();

    public GuestRepositoryDouble() {
        guests.add(theGuest);
    }

    @Override
    public List<Guest> findAll() {
        return new ArrayList<>(guests);
    }


    public Guest findById(int id) {
        return findAll().stream()
                .filter(i -> i.getGuest_id() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest add(Guest guest) throws DataException {
        if(guest == null){
            return null;
        }
        return guest;
    }

    @Override
    public boolean update(Guest guest) throws DataException{
        if(findById(guest.getGuest_id()) == null){
            return false;
        }
        return true;
    }

    @Override
    public Guest findByEmail(String email) {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
