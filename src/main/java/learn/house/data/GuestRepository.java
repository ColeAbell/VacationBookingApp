package learn.house.data;

import learn.house.models.Guest;

import java.util.List;

public interface GuestRepository {

    Guest add(Guest guest) throws DataException;

    List<Guest> findAll();

    boolean update(Guest guest) throws DataException;

    Guest findById(int id);


}
