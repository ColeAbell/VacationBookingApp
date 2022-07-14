package learn.house.ui;

import learn.house.data.DataException;
import learn.house.domain.GuestService;
import learn.house.domain.HostService;
import learn.house.domain.ReservationService;
import learn.house.domain.Result;
import learn.house.models.Guest;

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
                    break;
                case UPDATE_HOST:
                    break;
                case VIEW_HOST:
                    break;
                case MAKE_RESERVATION:
                    break;
                case UPDATE_RESERVATION:
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
}
