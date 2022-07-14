package learn.house.ui;

import learn.house.models.Guest;

import java.util.List;

public class View {

    public final ConsoleIO io;

    public View(ConsoleIO io) {
        this.io = io;
    }

    public MainMenuOption selectMainMenuOption() {
        displayHeader("Main Menu");
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (MainMenuOption option : MainMenuOption.values()) {
            io.printf("%s. %s%n", option.getValue(), option.getMessage());
            min = Math.min(min, option.getValue());
            max = Math.max(max, option.getValue());
        }

        String message = String.format("Select [%s-%s]: ", min, max - 1);
        return MainMenuOption.fromValue(io.readInt(message, min, max));
    }

    public void enterToContinue() {
        io.readString("Press [Enter] to continue.");
    }

    // display only
    public void displayHeader(String message) {
        io.println("");
        io.println(message);
        io.println("=".repeat(message.length()));
    }

    public void displayException(Exception ex) {
        displayHeader("ERROR:");
        io.println(ex.getMessage());
    }

    public void displayStatus(boolean success, String message) {
        displayStatus(success, List.of(message));
    }

    public void displayStatus(boolean success, List<String> messages) {
        displayHeader(success ? "Success" : "Error");
        for (String message : messages) {
            io.println(message);
        }
    }

    public Guest makeGuest(List<String> emails){
        displayHeader(MainMenuOption.ADD_GUEST.getMessage());
        Guest guest = new Guest();
        guest.setFirst_name(io.readRequiredString("Guest First Name:"));
        guest.setLast_name(io.readRequiredString("Guest Last Name:"));
        while(true) {
            String email = io.readEmail("Guest Email:");
            if (emails.contains(email)) {
                io.printf("%s is already in use. Please provide unique email.%n", email);
                continue;
            }
            guest.setEmail(email);
            break;
        }
        guest.setPhone(io.readPhoneNumber("Guest phone number:"));
        String state;
        while(true) {
            state = io.readRequiredString("Guest state (two letter abbreviation):");
            if (state.chars().allMatch(Character::isLetter) && state.length() == 2) {
                break;
            }
            System.out.println("State must be two letters");
        }
        guest.setState(state.toUpperCase());

        return guest;
    }

    public Guest updateGuest(Guest guest, List<String> emails){
        Guest newGuest = new Guest();
        newGuest.setGuest_id(guest.getGuest_id());
        io.printf("Updating Guest: %s %s%n", guest.getFirst_name(), guest.getLast_name());
        boolean update;
        update = io.readBoolean("Current First Name Is: " + guest.getFirst_name() + ". Update? [y/n]:");
        if(update){
            newGuest.setFirst_name(io.readRequiredString("Guest First Name:"));
        }
        else{
            newGuest.setFirst_name(guest.getFirst_name());
        }
        update = io.readBoolean("Current Last Name Is: " + guest.getLast_name() + ". Update? [y/n]:");
        if(update){
            newGuest.setLast_name(io.readRequiredString("Guest Last Name:"));
        }
        else{
            newGuest.setLast_name(guest.getLast_name());
        }
        update = io.readBoolean("Current Email Is: " + guest.getEmail() + ". Update? [y/n]:");
        if(update){
            while(true) {
                String email = io.readEmail("Guest Email:");
                if (emails.contains(email)) {
                    io.printf("%s is already in use. Please provide unique email.%n", email);
                    continue;
                }
                newGuest.setEmail(email);
                break;
            }
        }
        else{
            newGuest.setEmail(guest.getEmail());
        }
        update = io.readBoolean("Current Phone Number Is: " + guest.getPhone() + ". Update? [y/n]:");
        if(update){
            newGuest.setPhone(io.readPhoneNumber("Guest Phone Number:"));
        }
        else{
            newGuest.setPhone(guest.getPhone());
        }
        update = io.readBoolean("Current State Is: " + guest.getState() + ". Update? [y/n]:");
        if(update){
            String state;
            while(true) {
                state = io.readRequiredString("Guest state (two letter abbreviation):");
                if (state.chars().allMatch(Character::isLetter) && state.length() == 2) {
                    break;
                }
                System.out.println("State must be two letters");
            }
            newGuest.setState(state.toUpperCase());
        }
        else{
            newGuest.setState(guest.getState());
        }
        return newGuest;
    }
}
