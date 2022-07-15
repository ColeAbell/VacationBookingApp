package learn.house.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

public class Reservation implements Comparable<Reservation>{

    private int id;

    private LocalDate start_date;

    private LocalDate end_date;

    private int guest_id;

    private BigDecimal total;

    public Reservation(LocalDate start_date, LocalDate end_date, int guest_id, BigDecimal total) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.guest_id = guest_id;
        this.total = total;
    }

    public Reservation(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDate start_date) {
        this.start_date = start_date;
    }

    public LocalDate getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDate end_date) {
        this.end_date = end_date;
    }

    public int getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(int guest_id) {
        this.guest_id = guest_id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation reservation = (Reservation) o;
        return id == reservation.id &&
                guest_id == reservation.guest_id &&
                Objects.equals(start_date, reservation.start_date) &&
                Objects.equals(end_date, reservation.end_date) &&
                Objects.equals(total, reservation.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, guest_id, start_date, end_date, total);
    }

    @Override
    public int compareTo(Reservation r){
        return this.start_date.compareTo(r.start_date);
    }
}
