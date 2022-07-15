package learn.house.ui;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;


public class ConsoleIO {

    private final Scanner scanner = new Scanner(System.in);

    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printf(String format, Object... values) {
        System.out.printf(format, values);
    }

    public String readString(String prompt) {
        print(prompt);
        return scanner.nextLine();
    }

    public String readRequiredString(String prompt) {

        while (true) {
            String result = readString(prompt);
            if (!result.isBlank()) {
                return result;
            }
            println("Value is required");
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        BigDecimal value;
        while (true) {
            String input = readRequiredString(prompt);
            try {
                value = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
            } catch (NumberFormatException ex) {
                println("Input must be a number");
                continue;
            }
            if (value.compareTo(BigDecimal.ZERO) > 0) {
                return value;
            }
            println("Value must be greater than zero");
        }
    }

    public LocalDate readStartDate(String prompt) {
        println("Enter a date in MM/dd/yyyy format.");
        while (true) {
            String input = readRequiredString(prompt);
            LocalDate date;
            try {
                date = LocalDate.parse(input, formatter);
                if(date.isBefore(LocalDate.now())){
                    println("Date cannot be in the past");
                    continue;
                }
                else{
                    return date;
                }
            } catch (DateTimeParseException ex) {
                println("Invalid value, please enter a date in MM/dd/yyyy format.");
                continue;
            }
        }
    }

    public LocalDate readEndDate(String prompt, LocalDate start) {
        println("Enter a date in MM/dd/yyyy format.");
        while (true) {
            String input = readRequiredString(prompt);
            LocalDate date;
            try {
                date = LocalDate.parse(input, formatter);
                if(date.isBefore(LocalDate.now())){
                    println("Date cannot be in the past");
                    continue;
                }
                else if(date.isBefore(start) || date.equals(start)){
                    println("End date must be after start date");
                    continue;
                }
                else{
                    return date;
                }
            } catch (DateTimeParseException ex) {
                println("Invalid value, please enter a date in MM/dd/yyyy format.");
                continue;
            }
        }
    }

    public String readEmail(String prompt){
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        while(true){
            String email = readRequiredString(prompt);
            if(pattern.matcher(email).matches()){
                return email;
            }
            println("Email is not in valid format");
        }
    }

    public String readPhoneNumber(String prompt){
        while(true){
            String number = readRequiredString(prompt);
            try{
                Long.parseLong(number);
            }
            catch(NumberFormatException ex){
                println("Phone number cannot contain letters or symbols");
                continue;
            }
            if(number.length() != 10){
                println("Phone number must be 10 digits");
                continue;
            }
            return String.format("(%s) %s", number.substring(0, 3), number.substring(3));
        }
    }

    public int readInt(String prompt) {
        while (true) {
            try {
                return Integer.parseInt(readRequiredString(prompt));
            } catch (NumberFormatException ex) {
                println("Not a valid number");
            }
        }
    }

    public int readInt(String prompt, int min, int max) {
        while (true) {
            int result = readInt(prompt);
            if (result >= min && result <= max) {
                return result;
            }
            println(String.format("Number must be between %s and %s", min, max));
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            String input = readRequiredString(prompt).toLowerCase();
            if (input.equals("y")) {
                return true;
            } else if (input.equals("n")) {
                return false;
            }
            println("[INVALID] Please enter 'y' or 'n'.");
        }
    }

    public String readPostal(String prompt){
        while(true){
            String number = readRequiredString(prompt);
            try{
                Long.parseLong(number);
            }
            catch(NumberFormatException ex){
                println("Postal code cannot contain letters or symbols");
                continue;
            }
            if(number.length() != 5){
                println("Postal code must be 5 digits");
                continue;
            }
            return number;
        }
    }
}
