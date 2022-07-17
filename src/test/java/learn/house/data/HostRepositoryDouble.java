package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HostRepositoryDouble implements HostRepository{

    public final static Host theHost = new Host("Bob", "Bob", "phone", "address", "city",  "state",  "postal_code", new BigDecimal(2.00), new BigDecimal(3.00));
    private final ArrayList<Host> hosts = new ArrayList<>();

    public HostRepositoryDouble() {
        theHost.setId("3edda6bc-ab95-49a8-8962-d50b53f84b15");
        hosts.add(theHost);
    }

    @Override
    public List<Host> findAll() {
        return new ArrayList<>(hosts);
    }

    @Override
    public Host findByEmail(String email) {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    @Override
    public Host add(Host host) throws DataException{
        if(host == null){
            return null;
        }
        return host;
    }

    @Override
    public boolean update(Host host) throws DataException {

        if (host == null) {
            return false;
        }

        List<Host> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (host.getId().equals(all.get(i).getId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Host host) throws DataException{
        if (host == null){
            return false;
        }
        List<Host> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if(host.getId().equalsIgnoreCase(all.get(i).getId())){
                return true;
            }
        }
        return false;
    }
}
