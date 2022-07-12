package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class HostFileRepository implements HostRepository {

    public static final String delimiter = ",";

    public static final String header = "id,last_name,email,phone,address,city,state,postal_code,standard_rate,weekend_rate";

    private final String filePath;

    public HostFileRepository(String filepath) {
        this.filePath = filepath;
    }

    @Override
    public List<Host> findAll() {
        ArrayList<Host> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            reader.readLine();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                if (deserialize(line) != null) {
                    result.add(deserialize(line));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Host add(Host host) throws DataException {

        if (host == null) {
            return null;
        }

        List<Host> all = findAll();

        host = getUniqueId(host);

        all.add(host);
        writeAll(all);

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
                all.set(i, host);
                writeAll(all);
                return true;
            }
        }

        return false;
    }

    @Override
    public Host findByEmail(String email) {
        return findAll().stream()
                .filter(i -> i.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }

    protected void writeAll(List<Host> hosts) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(header);

            if (hosts == null) {
                return;
            }

            for (Host host : hosts) {
                writer.println(serialize(host));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }


    private String clean(String value) {
        return value.replace(delimiter, "");
    }

    private String serialize(Host host) {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s",
                host.getId(),
                clean(host.getLast_name()),
                clean(host.getEmail()),
                clean(host.getPhone()),
                clean(host.getAddress()),
                clean(host.getCity()),
                clean(host.getState()),
                clean(host.getPostal_code()),
                host.getStandard_rate().toString(),
                host.getWeekend_rate().toString()
        );
    }

    private Host deserialize(String line) {
        String[] fields = line.split(delimiter, -1);
        if (fields.length == 10) {
            Host host = new Host();
            host.setId(fields[0]);
            host.setLast_name(fields[1]);
            host.setEmail(fields[2]);
            host.setPhone(fields[3]);
            host.setAddress(fields[4]);
            host.setCity(fields[5]);
            host.setState(fields[6]);
            host.setPostal_code(fields[7]);
            host.setStandard_rate(new BigDecimal(fields[8]).setScale(2, RoundingMode.HALF_UP));
            host.setWeekend_rate(new BigDecimal(fields[9]).setScale(2, RoundingMode.HALF_UP));

        }
        return null;
    }

    private Host getUniqueId(Host host) {
        boolean unique = false;
        String id = "";
        do {
            id = java.util.UUID.randomUUID().toString();
            String finalId = id;
            unique = findAll().stream().anyMatch(g -> g.getId().equalsIgnoreCase(finalId));
        } while (!unique);
        host.setId(id);
        return host;
    }
}
