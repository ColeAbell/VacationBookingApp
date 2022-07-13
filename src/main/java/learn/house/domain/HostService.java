package learn.house.domain;

import learn.house.data.DataException;
import learn.house.data.GuestRepository;
import learn.house.data.HostRepository;
import learn.house.models.Guest;
import learn.house.models.Host;

import java.util.Objects;

public class HostService {

    private final HostRepository repository;

    public HostService(HostRepository repository){
        this.repository = repository;
    }

    public Result<Host> findByEmail(String email){
        Result<Host> result = new Result<>();
        if(repository.findByEmail(email) == null){
            result.addErrorMessage("No Host Exists With That Email");
            return result;
        }
        result.setPayload(repository.findByEmail(email));
        return result;
    }

    public Result<Host> add(Host host) throws DataException {
        Result<Host> result = validate(host);
        if(!result.isSuccess()){
            return result;
        }
        for (Host h : repository.findAll()){
            if(Objects.equals(h.getLast_name(), host.getLast_name())
                    && Objects.equals(h.getEmail(), host.getEmail())
                    && Objects.equals(h.getPhone(), host.getPhone())
                    && Objects.equals(h.getAddress(), host.getAddress())
                    && Objects.equals(h.getCity(), host.getCity())
                    && Objects.equals(h.getState(), host.getState())
                    && Objects.equals(h.getPostal_code(), host.getPostal_code())
                    && Objects.equals(h.getStandard_rate(), host.getStandard_rate())
                    && Objects.equals(h.getWeekend_rate(), host.getWeekend_rate())
            ){
                result.addErrorMessage("Host cannot be duplicate");
                return result;
            }
        }
        result.setPayload(repository.add(host));
        return result;
    }

    public Result<Host> update(Host host) throws DataException {
        Result<Host> result = validate(host);
        if (!result.isSuccess()) {
            return result;
        }
        for (Host h : repository.findAll()) {
            if (Objects.equals(h.getLast_name(), host.getLast_name())
                    && Objects.equals(h.getEmail(), host.getEmail())
                    && Objects.equals(h.getPhone(), host.getPhone())
                    && Objects.equals(h.getAddress(), host.getAddress())
                    && Objects.equals(h.getCity(), host.getCity())
                    && Objects.equals(h.getState(), host.getState())
                    && Objects.equals(h.getPostal_code(), host.getPostal_code())
                    && Objects.equals(h.getStandard_rate(), host.getStandard_rate())
                    && Objects.equals(h.getWeekend_rate(), host.getWeekend_rate())
            ) {
                result.addErrorMessage("Host cannot be duplicate");
                return result;
            }
        }
        repository.update(host);
        result.setPayload(host);
        return result;

    }

    public Result<Host> validate(Host host){
        Result<Host> result = new Result<>();
        if (host == null) {
            result.addErrorMessage("Host cannot be null");
            return result;
        }

        if (host.getLast_name() == null || host.getLast_name().trim().length() == 0) {
            result.addErrorMessage("Last name is required");
            return result;
        }

        if (host.getEmail() == null || host.getEmail().trim().length() == 0) {
            result.addErrorMessage("Email is required");
            return result;
        }

        if (host.getAddress() == null || host.getAddress().trim().length() == 0) {
            result.addErrorMessage("Address is required");
            return result;
        }

        if (host.getPhone() == null || host.getPhone().trim().length() == 0) {
            result.addErrorMessage("Phone number is required");
            return result;
        }

        if (host.getState() == null || host.getState().trim().length() == 0) {
            result.addErrorMessage("State is required");
            return result;
        }

        if (host.getCity() == null || host.getCity().trim().length() == 0) {
            result.addErrorMessage("City is required");
            return result;
        }

        if (host.getPostal_code() == null || host.getPostal_code().trim().length() == 0) {
            result.addErrorMessage("Postal code is required");
            return result;
        }

        return result;

    }
}
