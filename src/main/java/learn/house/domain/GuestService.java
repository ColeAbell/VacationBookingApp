package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.models.Guest;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class GuestService {

    private final GuestRepository repository;

    public GuestService(GuestRepository repository){
        this.repository = repository;
    }

    public Result<Guest> findByEmail(String email){
        Result<Guest> result = new Result<>();
        if(repository.findByEmail(email) == null){
            result.addErrorMessage("No Guest Exists With That Email");
            return result;
        }
        result.setPayload(repository.findByEmail(email));
        return result;
    }

    public Guest findById(int id){
        return repository.findAll().stream().filter(g -> g.getGuest_id() == id).findFirst().get();
    }

    public List<String> getEmails(){
        return repository.findAll().stream().map(g -> g.getEmail()).collect(Collectors.toList());
    }

    public List<String> getPhoneNumbers(){
        return repository.findAll().stream().map(g -> g.getPhone()).collect(Collectors.toList());
    }

    public Result<Guest> add(Guest guest) throws DataException{
        Result<Guest> result = validate(guest);
        if(!result.isSuccess()){
            return result;
        }
        for (Guest g : repository.findAll()){
            if(Objects.equals(g.getFirst_name(), guest.getFirst_name())
                    && Objects.equals(g.getLast_name(), guest.getLast_name())
                    && Objects.equals(g.getEmail(), guest.getEmail())
                    && Objects.equals(g.getPhone(), guest.getPhone())
                    && Objects.equals(g.getState(), guest.getState())
            ){
                result.addErrorMessage("Guest cannot be duplicate");
                return result;
            }
        }
        result.setPayload(repository.add(guest));
        return result;
    }

    public Result<Guest> update(Guest guest) throws DataException {
        Result<Guest> result = validate(guest);
        if (!result.isSuccess()) {
            return result;
        }
        for (Guest g : repository.findAll()) {
            if (Objects.equals(g.getFirst_name(), guest.getFirst_name())
                    && Objects.equals(g.getLast_name(), guest.getLast_name())
                    && Objects.equals(g.getEmail(), guest.getEmail())
                    && Objects.equals(g.getPhone(), guest.getPhone())
                    && Objects.equals(g.getState(), guest.getState())
            ) {
                result.addErrorMessage("Guest cannot be duplicate");
                return result;
            }
        }
        repository.update(guest);
        result.setPayload(guest);
        return result;
    }

    public Result<Guest> validate(Guest guest){
        Result<Guest> result = new Result<>();
        if (guest == null) {
            result.addErrorMessage("Guest cannot be null");
            return result;
        }

        if (guest.getFirst_name() == null || guest.getFirst_name().trim().length() == 0) {
            result.addErrorMessage("First name is required");
            return result;
        }

        if (guest.getLast_name() == null || guest.getLast_name().trim().length() == 0) {
            result.addErrorMessage("Last name is required");
            return result;
        }

        if (guest.getEmail() == null || guest.getEmail().trim().length() == 0) {
            result.addErrorMessage("Email is required");
            return result;
        }

        if (guest.getPhone() == null || guest.getPhone().trim().length() == 0) {
            result.addErrorMessage("Phone number is required");
            return result;
        }

        if (guest.getState() == null || guest.getState().trim().length() == 0) {
            result.addErrorMessage("State is required");
            return result;
        }

        return result;

    }
}
