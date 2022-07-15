package learn.house.ui;

import learn.house.data.DataException;
import learn.house.domain.GuestService;
import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.domain.Result;
import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Controller {

    private final GuestService guestService;

    private final HostService hostService;

    private final ReservationService reservationService;

    private final View view;

    public Controller (GuestService guestService, HostService hostService, ReservationService reservationService, View view){
        this.guestService = guestService;
        this.hostService = hostService;
        this.reservationService = reservationService;
        this.view = view;
    }

    public void run() {
        view.displayHeader("Welcome to Don't Wreck My House LLC");
        try {
            runAppLoop();
        } catch (DataException ex) {
            view.displayException(ex);
        }
        view.displayHeader("Thank You For Not Wrecking My House");
    }

    private void runAppLoop() throws DataException {
        MainMenuOption option;
        do {
            option = view.selectMainMenuOption();
            switch (option) {
                case ADD_GUEST:
                    addGuest();
                    break;
                case UPDATE_GUEST:
                    updateGuest();
                    break;
                case VIEW_GUEST:
                    viewGuest();
                    break;
                case ADD_HOST:
                    addHost();
                    break;
                case UPDATE_HOST:
                    updateHost();
                    break;
                case VIEW_HOST:
                    viewHost();
                    break;
                case MAKE_RESERVATION:
                    addReservation();
                    break;
                case UPDATE_RESERVATION:
                    updateReservation();
                    break;
                case DELETE_RESERVATION:
                    break;
            }
        } while (option != MainMenuOption.EXIT);
    }

    private void addGuest() throws DataException{
        Guest guest = view.makeGuest(guestService.getEmails());
        Result<Guest> result = guestService.add(guest);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Guest %s created.", result.getPayload().getFirst_name() + " " + result.getPayload().getLast_name());
            view.displayStatus(true, successMessage);
        }
        view.enterToContinue();
    }

    private void updateGuest() throws DataException{
        Result<Guest> result;
        do {
            result = guestService.findByEmail(view.io.readEmail("Please provide the email of the guest you wish to update:"));
            if (!result.isSuccess()) {
                view.displayStatus(false, result.getErrorMessages());
                if (!view.io.readBoolean("Try another email? [y/n]:")) {
                    return;
                } else {
                    continue;
                }
            }
            break;
        }while(true);
        Guest guest = result.getPayload();
        Guest updatedGuest = view.updateGuest(guest, guestService.getEmails());
        Result<Guest> updateResult = guestService.update(updatedGuest);
        if (!updateResult.isSuccess()) {
            view.displayStatus(false, updateResult.getErrorMessages());
        } else {
            String successMessage = String.format("Guest %s updated.", updateResult.getPayload().getFirst_name() + " " + updateResult.getPayload().getLast_name());
            view.displayStatus(true, successMessage);
        }
        view.enterToContinue();
    }

    private void viewGuest() throws DataException{
        Result<Guest> result;
        do {
            result = guestService.findByEmail(view.io.readEmail("Please provide the email of the guest profile:"));
            if (!result.isSuccess()) {
                view.displayStatus(false, result.getErrorMessages());
                if (!view.io.readBoolean("Try another email? [y/n]:")) {
                    return;
                } else {
                    continue;
                }
            }
            break;
        }while(true);
        Guest guest = result.getPayload();
        view.io.println("Guest Profile:");
        view.io.printf("Name: %s %s%nEmail: %s%nPhone: %s%nState: %s%n", guest.getFirst_name(), guest.getLast_name(), guest.getEmail(), guest.getPhone(), guest.getState());
        view.enterToContinue();
    }

    private void addHost() throws DataException{
        Host host = view.makeHost(hostService.getEmails());
        Result<Host> result = hostService.add(host);
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Host %s created.", result.getPayload().getLast_name());
            view.displayStatus(true, successMessage);
        }
        view.enterToContinue();
    }

    private void updateHost() throws DataException{
        Result<Host> result;
        do {
            result = hostService.findByEmail(view.io.readEmail("Please provide the email of the host you wish to update:"));
            if (!result.isSuccess()) {
                view.displayStatus(false, result.getErrorMessages());
                if (!view.io.readBoolean("Try another email? [y/n]:")) {
                    return;
                } else {
                    continue;
                }
            }
            break;
        }while(true);
        Host host = result.getPayload();
        Host updatedHost = view.updateHost(host, hostService.getEmails());
        Result<Host> updateResult = hostService.update(updatedHost);
        if (!updateResult.isSuccess()) {
            view.displayStatus(false, updateResult.getErrorMessages());
        } else {
            String successMessage = String.format("Host %s updated.", updateResult.getPayload().getLast_name());
            view.displayStatus(true, successMessage);
        }
        view.enterToContinue();
    }

    private void viewHost() throws DataException{
        Result<Host> result;
        do {
            result = hostService.findByEmail(view.io.readEmail("Please provide the email of the host profile:"));
            if (!result.isSuccess()) {
                view.displayStatus(false, result.getErrorMessages());
                if (!view.io.readBoolean("Try another email? [y/n]:")) {
                    return;
                } else {
                    continue;
                }
            }
            break;
        }while(true);
        Host host = result.getPayload();
        view.io.println("Host Profile:");
        view.io.printf("Last Name: %s%nEmail: %s%nPhone: %s%nAddress: %s%nCity: %s%nState: %s%nPostal: %s%nStandard Rate: %s%nWeekend Rate: %s%n", host.getLast_name(), host.getEmail(), host.getPhone(), host.getAddress(), host.getCity(), host.getState(), host.getPostal_code(), host.getStandard_rate(), host.getWeekend_rate());
        view.enterToContinue();
    }

    private void addReservation() throws DataException {
        Reservation reservation = new Reservation();
        Result<Host> hostResult;
        Host host;
        while (true) {
            do {
                hostResult = hostService.findByEmail(view.io.readEmail("Please provide the email of the host for the reservation:"));
                if (!hostResult.isSuccess()) {
                    view.displayStatus(false, hostResult.getErrorMessages());
                    if (!view.io.readBoolean("Try another email? [y/n]:")) {
                        return;
                    } else {
                        continue;
                    }
                }
                break;
            } while (true);
            host = hostResult.getPayload();
            view.io.println("Host Profile:");
            view.io.printf("Last Name: %s%nEmail: %s%nPhone: %s%nAddress: %s%nCity: %s%nState: %s%nPostal: %s%nStandard Rate: %s%nWeekend Rate: %s%n", host.getLast_name(), host.getEmail(), host.getPhone(), host.getAddress(), host.getCity(), host.getState(), host.getPostal_code(), host.getStandard_rate(), host.getWeekend_rate());
            if (!view.io.readBoolean("Is this the host you want? [y/n]:")) {
                if (!view.io.readBoolean("Try another email? [y/n]:")) {
                    return;
                }
                continue;
            }
            break;
        }
        Result<Guest> guestResult;
        Guest guest;
        while (true) {
            do {
                guestResult = guestService.findByEmail(view.io.readEmail("Please provide the email of the guest for the reservation:"));
                if (!guestResult.isSuccess()) {
                    view.displayStatus(false, guestResult.getErrorMessages());
                    if (!view.io.readBoolean("Try another email? [y/n]:")) {
                        return;
                    } else {
                        continue;
                    }
                }
                break;
            } while (true);
            guest = guestResult.getPayload();
            view.io.println("Guest Profile:");
            view.io.printf("Name: %s %s%nEmail: %s%nPhone: %s%nState: %s%n", guest.getFirst_name(), guest.getLast_name(), guest.getEmail(), guest.getPhone(), guest.getState());
            if (!view.io.readBoolean("Is this the guest you want? [y/n]:")) {
                if (!view.io.readBoolean("Try another email? [y/n]:")) {
                    return;
                }
                continue;
            }
            break;
        }
        reservation.setGuest_id(guest.getGuest_id());
        LocalDate start;
        LocalDate end;
        while(true){
            start = view.io.readStartDate("Reservation Start Date:");
            end = view.io.readEndDate("Reservation End Date:", start);
            List<Reservation> conflicts = reservationService.checkForDoubleBook(host.getId(), start, end);
            if(conflicts.size() == 0 || conflicts == null){
                break;
            }
            view.io.println("These dates conflict with the following reservations:");
            displayReservation(conflicts);
            if(!view.io.readBoolean("Try different dates? [y/n]:")){
                return;
            }
        }
        reservation.setStart_date(start);
        reservation.setEnd_date(end);
        reservation.setTotal(getTotal(start, end, host));
        Result<Reservation> result = reservationService.add(reservation, host.getId());
        if (!result.isSuccess()) {
            view.displayStatus(false, result.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation For Host %s created.", host.getLast_name());
            view.displayStatus(true, successMessage);
            List<Reservation> r = new ArrayList<>();
            r.add(reservation);
            displayReservation(r);
        }
        view.enterToContinue();
    }

    public void updateReservation() throws DataException{
        Reservation reservation = new Reservation();
        Result<Host> hostResult;
        Host host;
        while (true) {
            do {
                hostResult = hostService.findByEmail(view.io.readEmail("Please provide the email of the host for the reservation you want to edit:"));
                if (!hostResult.isSuccess()) {
                    view.displayStatus(false, hostResult.getErrorMessages());
                    if (!view.io.readBoolean("Try another email? [y/n]:")) {
                        return;
                    } else {
                        continue;
                    }
                }
                break;
            } while (true);
            host = hostResult.getPayload();
            view.io.println("Host Profile:");
            view.io.printf("Last Name: %s%nEmail: %s%nPhone: %s%nAddress: %s%nCity: %s%nState: %s%nPostal: %s%nStandard Rate: %s%nWeekend Rate: %s%n", host.getLast_name(), host.getEmail(), host.getPhone(), host.getAddress(), host.getCity(), host.getState(), host.getPostal_code(), host.getStandard_rate(), host.getWeekend_rate());
            if (!view.io.readBoolean("Is this the host you want? [y/n]:")) {
                if (!view.io.readBoolean("Try another email? [y/n]:")) {
                    return;
                }
                continue;
            }
            break;
        }
        List<Reservation> reservations = reservationService.findByHost(host.getId());
        if(reservations.size() == 0){
            System.out.println("Host has no active reservations");
            view.enterToContinue();
            return;
        }
        int smallest = reservations.stream().mapToInt(r -> r.getId()).min().getAsInt();
        int largest = reservations.stream().mapToInt(r -> r.getId()).max().getAsInt();
        displayReservation(reservations);
        int choice = view.io.readInt(String.format("Choose a reservation ID [%s-%s]:", smallest, largest), smallest, largest);
        reservation = reservations.stream().filter(r -> r.getId() == choice).findFirst().get();
        LocalDate start;
        LocalDate end;
        boolean update;
        while(true){
            update = view.io.readBoolean("Current Start Date is " + reservation.getStart_date() + ". Update? [y/n]:");
            if(update){
                start = view.io.readStartDate("Reservation Start Date:");
            }
            else{
                start = reservation.getStart_date();
            }
            if(reservation.getEnd_date().compareTo(start) <= 0){
                view.io.printf("Select New End Date After %s%n", start);
                end = view.io.readEndDate("Reservation End Date:", start);
            }
            else{
                update = view.io.readBoolean("Current End Date is " + reservation.getEnd_date() + ". Update? [y/n]:");
                if(update){
                    end = view.io.readEndDate("Reservation End Date:", start);
                }
                else{
                    end = reservation.getEnd_date();
                }
            }
            List<Reservation> conflicts = reservationService.checkForDoubleBook(host.getId(), start, end);
            if(conflicts.size() == 0 || conflicts == null){
                break;
            }
            if(conflicts.size()  == 1 && conflicts.get(0).getId() == reservation.getId()){
                break;
            }
            view.io.println("New reservation dates conflict with the following reservations:");
            displayReservation(conflicts);
            if(!view.io.readBoolean("Try different dates? [y/n]:")){
                return;
            }
        }
        reservation.setStart_date(start);
        reservation.setEnd_date(end);
        reservation.setTotal(getTotal(start, end, host));
        view.displayHeader("Summary Of Updated Reservation");
        view.io.printf("Start Date: %s%nEnd Date: %s%nTotal: %s%n", start, end, reservation.getTotal());
        if(!view.io.readBoolean("Please Confirm [y/n]:")){
            return;
        }
        Result<Reservation> updateResult = reservationService.update(reservation, host.getId());
        if (!updateResult.isSuccess()) {
            view.displayStatus(false, updateResult.getErrorMessages());
        } else {
            String successMessage = String.format("Reservation For Host %s Updated.", host.getLast_name());
            view.displayStatus(true, successMessage);
            List<Reservation> r = new ArrayList<>();
            r.add(reservation);
            displayReservation(r);
        }
        view.enterToContinue();


    }

    public void displayReservation(List<Reservation> reservations){
        Collections.sort(reservations);
        if (reservations.size() == 0) {
            System.out.println();
            System.out.println("No reservations found.");
        } else {
            String rowFormat = "| %-5s | %-10s | %-10s | %-15s | %-7s |%n";
            System.out.printf(rowFormat, "ID", "Start Date", "End Date", "Guest", "Total");
            int b = String.format(rowFormat, "ID", "Start Date", "End Date", "Guest", "Total").length();
            String border = "_".repeat(b);
            for (Reservation r : reservations) {
                System.out.print(border);
                System.out.println();
                System.out.printf(rowFormat,
                        r.getId(),
                        r.getStart_date(),
                        r.getEnd_date(),
                        guestService.findById(r.getGuest_id()).fullName(),
                        r.getTotal().toString()
                );
            }
        }
    }

    public BigDecimal getTotal(LocalDate start, LocalDate end, Host host){
        int weekday = 0;
        int weekend = 0;
        LocalDate test = start;
        while(test.isBefore(end) || test.equals(end)){
            if(test.getDayOfWeek() == DayOfWeek.SATURDAY || test.getDayOfWeek() == DayOfWeek.SUNDAY){
                weekend += 1;
            }
            else{
                weekday += 1;
            }
            test = test.plusDays(1);
        }
        BigDecimal wkday = new BigDecimal(weekday);
        BigDecimal wkend = new BigDecimal(weekend);
        BigDecimal weekdayAmount = wkday.multiply(host.getStandard_rate());
        BigDecimal weekendAmount  = wkend.multiply(host.getWeekend_rate());
        BigDecimal total = weekdayAmount.add(weekendAmount).setScale(2, RoundingMode.HALF_UP);
        return total;
    }
}
