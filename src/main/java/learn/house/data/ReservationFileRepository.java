package learn.house.data;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReservationFileRepository implements ReservationRepository{

    public static final String delimiter = ",";

    public static final String header = "id,start_date,end_date,guest_id,total";

    private final String directory;

    public ReservationFileRepository(String directory){
        this.directory = directory;
    }

    private String getFilePath(String hostId) {
        return Paths.get(directory, hostId + ".csv").toString();
    }

    @Override
    public List<Reservation> findByHost(String hostId) {
        ArrayList<Reservation> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(getFilePath(hostId)))) {

            reader.readLine(); // read header

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {

                if (!(deserialize(line) == null)) {
                    result.add(deserialize(line));
                }
            }
        } catch (IOException ex) {
            // don't throw on read
        }
        return result;
    }

    @Override
    public Reservation add(Reservation reservation, String hostId) throws DataException {
        if(reservation == null){
            return null;
        }
        List<Reservation> all = findByHost(hostId);
        int nextId = all.stream()
                .mapToInt(Reservation::getId)
                .max()
                .orElse(0) + 1;
        reservation.setId(nextId);
        all.add(reservation);
        writeAll(all, hostId);
        return reservation;
    }

    @Override
    public boolean update(Reservation reservation, String hostId) throws DataException {
        List<Reservation> all = findByHost(hostId);
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == reservation.getId()) {
                all.set(i, reservation);
                writeAll(all, hostId);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(Reservation reservation, String hostId) throws DataException {
        List<Reservation> all = findByHost(hostId);
        int id = reservation.getId();
        for (int i = 0; i < all.size(); i++) {
            if (all.get(i).getId() == reservation.getId()) {
                all.remove(i);
                writeAll(all, hostId);
                all = findByHost(hostId);
                for (int j = 0; j < all.size(); j++) {
                    int thisId = all.get(i).getId();
                    if(thisId > id){
                        Reservation adjusted = all.get(i);
                        adjusted.setId(thisId - 1);
                        all.set(j, adjusted);
                    }
                }
                writeAll(all, hostId);
                return true;
            }
        }
        return false;
    }

    private void writeAll(List<Reservation> reservations, String hostId) throws DataException {
        try (PrintWriter writer = new PrintWriter(getFilePath(hostId))) {

            writer.println(header);

            for (Reservation reservation : reservations) {
                writer.println(serialize(reservation));
            }
        } catch (FileNotFoundException ex) {
            throw new DataException(ex);
        }
    }

    private String clean(String value) {
        return value.replace(delimiter, "");
    }

    private String serialize(Reservation reservation) {
        return String.format("%s,%s,%s,%s,%s",
                reservation.getId(),
                reservation.getStart_date(),
                reservation.getEnd_date(),
                reservation.getGuest_id(),
                reservation.getTotal().toString()
        );
    }

    private Reservation deserialize(String line) {
        String[] fields = line.split(delimiter, -1);
        if (fields.length == 5) {
            Reservation reservation = new Reservation();
            reservation.setId(Integer.parseInt(fields[0]));
            reservation.setStart_date(LocalDate.parse(fields[1]));
            reservation.setEnd_date(LocalDate.parse(fields[2]));
            reservation.setGuest_id(Integer.parseInt(fields[3]));
            reservation.setTotal(new BigDecimal(fields[4]).setScale(2, RoundingMode.HALF_UP));
            return reservation;
        }
        return null;
    }
}
