package learn.house.models;

import java.util.Objects;

public class Guest {

    private int guest_id;

    private String first_name;

    private String last_name;

    private String email;

    private String phone;

    private String state;

    public Guest(int guest_id, String first, String last, String email, String phone, String state){
        this.first_name = first;
        this.last_name = last;
        this.email = email;
        this.phone = phone;
        this.state = state;
        this.guest_id = guest_id;
    }

    public Guest(){

    }

    public int getGuest_id() {
        return guest_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getState() {
        return state;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return guest_id == guest.guest_id &&
                Objects.equals(first_name, guest.first_name) &&
                Objects.equals(last_name, guest.last_name) &&
                Objects.equals(email, guest.email) &&
                Objects.equals(phone, guest.phone) &&
                Objects.equals(state, guest.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guest_id, first_name, last_name, email, phone, state);
    }

    public String fullName(){
        return this.getFirst_name() + " " + this.getLast_name();
    }
}
