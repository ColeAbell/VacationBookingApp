package learn.house.data;

import learn.house.models.Guest;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GuestFileRepository implements GuestRepository {

    public static final String delimiter = ",";

    public static final String header = "guest_id,first_name,last_name,email,phone,state";

    private final String filePath;

    public GuestFileRepository(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<Guest> findAll() {
        ArrayList<Guest> result = new ArrayList<>();
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
    public Guest findById(int id) {
        return findAll().stream()
                .filter(i -> i.getGuest_id() == id)
                .findFirst()
                .orElse(null);
    }

    @Override
    public Guest add(Guest guest) throws DataException {

        if (guest == null) {
            return null;
        }

        List<Guest> all = findAll();

        int nextId = all.stream()
                .mapToInt(Guest::getGuest_id)
                .max()
                .orElse(0) + 1;

        guest.setGuest_id(nextId);

        all.add(guest);
        writeAll(all);

        return guest;
    }

    public boolean update(Guest guest) throws DataException {

        if (guest == null) {
            return false;
        }

        List<Guest> all = findAll();
        for (int i = 0; i < all.size(); i++) {
            if (guest.getGuest_id() == all.get(i).getGuest_id()) {
                all.set(i, guest);
                writeAll(all);
                return true;
            }
        }

        return false;
    }


    protected void writeAll(List<Guest> guests) throws DataException {
        try (PrintWriter writer = new PrintWriter(filePath)) {

            writer.println(header);

            if (guests == null) {
                return;
            }

            for (Guest guest : guests) {
                writer.println(serialize(guest));
            }

        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }


    private String clean(String value) {
        return value.replace(delimiter, "");
    }

    private String serialize(Guest guest) {
        return String.format("%s,%s,%s,%s,%s,%s",
                guest.getGuest_id(),
                clean(guest.getFirst_name()),
                clean(guest.getLast_name()),
                clean(guest.getEmail()),
                clean(guest.getPhone()),
                clean(guest.getState())
        );
    }

    private Guest deserialize(String line) {
        String[] fields = line.split(delimiter, -1);
        if (fields.length == 6) {
            Guest guest = new Guest();
            guest.setGuest_id(Integer.parseInt(fields[0]));
            guest.setFirst_name(fields[1]);
            guest.setLast_name(fields[2]);
            guest.setEmail(fields[3]);
            guest.setPhone(fields[4]);
            guest.setState(fields[5]);
            return guest;
        }
        return null;
    }




}
