package learn.house.models;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Host {

    private String id;

    private String last_name;

    private String email;

    private String phone;

    private String address;

    private String city;

    private String state;

    private String postal_code;

    private BigDecimal standard_rate;

    private BigDecimal weekend_rate;

    public Host(String last_name, String email, String phone, String address, String city, String state, String postal_code, BigDecimal standard_rate, BigDecimal weekend_rate) {
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.standard_rate = standard_rate.setScale(2, RoundingMode.HALF_UP);
        this.weekend_rate = weekend_rate.setScale(2, RoundingMode.HALF_UP);
    }

    public Host(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal_code() {
        return postal_code;
    }

    public void setPostal_code(String postal_code) {
        this.postal_code = postal_code;
    }

    public BigDecimal getStandard_rate() {
        return standard_rate;
    }

    public void setStandard_rate(BigDecimal standard_rate) {
        this.standard_rate = standard_rate;
    }

    public BigDecimal getWeekend_rate() {
        return weekend_rate;
    }

    public void setWeekend_rate(BigDecimal weekend_rate) {
        this.weekend_rate = weekend_rate;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return  Objects.equals(id, host.id) &&
                Objects.equals(last_name, host.last_name) &&
                Objects.equals(email, host.email) &&
                Objects.equals(phone, host.phone) &&
                Objects.equals(address, host.address) &&
                Objects.equals(city, host.city) &&
                Objects.equals(postal_code, host.postal_code) &&
                Objects.equals(standard_rate, host.standard_rate) &&
                Objects.equals(weekend_rate, host.weekend_rate) &&
                Objects.equals(state, host.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, last_name, email, phone, address, city, postal_code, standard_rate, weekend_rate, state);
    }


}
