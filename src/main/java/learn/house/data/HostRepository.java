package learn.house.data;

import learn.house.models.*;

import java.util.List;

public interface HostRepository {

    Host add(Host host) throws DataException;

    List<Host> findAll();

    boolean update(Host host) throws DataException;

    Host findByEmail(String email);
}
