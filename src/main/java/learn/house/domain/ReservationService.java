package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.HostRepository;
import learn.house.data.ReservationRepository;
import learn.house.models.Guest;
import learn.house.models.Reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReservationService {

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository){
        this.repository = repository;
    }

    public List<Reservation> findByHost(String hostId) {
        return repository.findByHost(hostId);
    }

    public List<Reservation> checkForDoubleBook(String hostId, LocalDate start, LocalDate end){
        List<Reservation> current = findByHost(hostId);
        List<Reservation> doubleBook = current.stream().filter(r -> (start.isBefore(r.getStart_date()) && end.isAfter(r.getEnd_date())) || (start.isEqual(r.getStart_date()) || start.isEqual(r.getEnd_date())) || (end.isEqual(r.getStart_date()) || end.isEqual(r.getEnd_date())) || (start.isAfter(r.getStart_date()) && start.isBefore(r.getEnd_date())) || (end.isAfter(r.getStart_date()) && end.isBefore(r.getEnd_date()))).collect(Collectors.toList());
        return doubleBook;
    }

    public Result<Reservation> add(Reservation reservation, String hostId) throws DataException{
        Result<Reservation> result = new Result<>();
        result.setPayload(repository.add(reservation, hostId));
        return result;
    }

    public Result<Reservation> update(Reservation reservation, String hostId) throws DataException {
        Result<Reservation> result = new Result<>();
        for (Reservation r : repository.findByHost(hostId)) {
            if (Objects.equals(r.getGuest_id(), reservation.getGuest_id())
                    && Objects.equals(r.getStart_date(), reservation.getStart_date())
                    && Objects.equals(r.getEnd_date(), reservation.getEnd_date())
                    && Objects.equals(r.getTotal(), reservation.getTotal())
            ) {
                result.addErrorMessage("Reservation cannot be duplicate");
                return result;
            }
        }
        boolean outcome = repository.update(reservation, hostId);
        if(!outcome){
            result.addErrorMessage("Reservation to be updated does not exist");
            return result;
        }
        result.setPayload(reservation);
        return result;
    }

    public Result<Reservation> delete(Reservation reservation, String hostId) throws DataException{
        Result<Reservation> result = new Result<>();
        boolean outcome = repository.delete(reservation, hostId);
        if(!outcome){
            result.addErrorMessage("Reservation to be deleted does not exist");
        }
        result.setPayload(reservation);
        return result;
    }





}
