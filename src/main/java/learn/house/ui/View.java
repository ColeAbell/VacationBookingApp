package learn.house.ui;

import learn.house.models.Guest;
import learn.house.models.Host;
import learn.house.models.Reservation;

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

    public Host makeHost(List<String> emails){
        displayHeader(MainMenuOption.ADD_HOST.getMessage());
        Host host = new Host();
        host.setLast_name(io.readRequiredString("Host Last Name:"));
        while(true) {
            String email = io.readEmail("Host Email:");
            if (emails.contains(email)) {
                io.printf("%s is already in use. Please provide unique email.%n", email);
                continue;
            }
            host.setEmail(email);
            break;
        }
        host.setPhone(io.readPhoneNumber("Host phone number (########## format):"));
        host.setAddress(io.readRequiredString("Host Address:"));
        host.setCity(io.readRequiredString("Host City:"));
        String state;
        while(true) {
            state = io.readRequiredString("Host state (two letter abbreviation):");
            if (state.chars().allMatch(Character::isLetter) && state.length() == 2) {
                break;
            }
            System.out.println("State must be two letters");
        }
        host.setState(state.toUpperCase());
        host.setPostal_code(io.readPostal("Host Postal Code:"));
        host.setStandard_rate(io.readBigDecimal("Host Standard Rate:"));
        host.setWeekend_rate(io.readBigDecimal("Host Weekend Rate:"));
        return host;
    }

    public Host updateHost(Host host, List<String> emails){
        Host newHost = new Host();
        newHost.setId(host.getId());
        io.printf("Updating Host: %s%n", host.getLast_name());
        boolean update;
        update = io.readBoolean("Current Last Name Is: " + host.getLast_name() + ". Update? [y/n]:");
        if(update){
            newHost.setLast_name(io.readRequiredString("Host Last Name:"));
        }
        else{
            newHost.setLast_name(host.getLast_name());
        }
        update = io.readBoolean("Current Email Is: " + host.getEmail() + ". Update? [y/n]:");
        if(update){
            while(true) {
                String email = io.readEmail("Host Email:");
                if (emails.contains(email)) {
                    io.printf("%s is already in use. Please provide unique email.%n", email);
                    continue;
                }
                newHost.setEmail(email);
                break;
            }
        }
        else{
            newHost.setEmail(host.getEmail());
        }
        update = io.readBoolean("Current Phone Number Is: " + host.getPhone() + ". Update? [y/n]:");
        if(update){
            newHost.setPhone(io.readPhoneNumber("Host Phone Number:"));
        }
        else{
            newHost.setPhone(host.getPhone());
        }
        update = io.readBoolean("Current Address Is: " + host.getAddress() + ". Update? [y/n]:");
        if(update){
            newHost.setAddress(io.readRequiredString("Host Address:"));
        }
        else{
            newHost.setAddress(host.getAddress());
        }
        update = io.readBoolean("Current City Is: " + host.getCity() + ". Update? [y/n]:");
        if(update){
            newHost.setCity(io.readRequiredString("Host City:"));
        }
        else{
            newHost.setCity(host.getCity());
        }
        update = io.readBoolean("Current State Is: " + host.getState() + ". Update? [y/n]:");
        if(update){
            String state;
            while(true) {
                state = io.readRequiredString("Host state (two letter abbreviation):");
                if (state.chars().allMatch(Character::isLetter) && state.length() == 2) {
                    break;
                }
                System.out.println("State must be two letters");
            }
            newHost.setState(state.toUpperCase());
        }
        else{
            newHost.setState(host.getState());
        }
        update = io.readBoolean("Current Postal Code Is: " + host.getPostal_code() + ". Update? [y/n]:");
        if(update){
            newHost.setPostal_code(io.readPostal("Host Postal Code:"));
        }
        else{
            newHost.setPostal_code(host.getPostal_code());
        }
        update = io.readBoolean("Current Standard Rate Is: " + host.getStandard_rate().toString() + ". Update? [y/n]:");
        if(update){
            newHost.setStandard_rate(io.readBigDecimal("Host Standard Rate:"));
        }
        else{
            newHost.setStandard_rate(host.getStandard_rate());
        }
        update = io.readBoolean("Current Weekend Rate Is: " + host.getWeekend_rate().toString() + ". Update? [y/n]:");
        if(update){
            newHost.setWeekend_rate(io.readBigDecimal("Host Weekend Rate:"));
        }
        else{
            newHost.setWeekend_rate(host.getWeekend_rate());
        }
        return newHost;
    }

}
