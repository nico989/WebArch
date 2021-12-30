package it.unitn.disi.vinci.server.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Reservation implements Serializable {

    private static final long serialVersionUID = -4581598877222772474L;

    public enum HalfBoard {
        Yes,
        No,
        Null
    }

    private Long id;

    private Guest guest;

    private Accommodation accommodation;

    private int nPersons;

    private String creditCardNumber;

    private Date dateFrom;

    private Date dateTo;

    private HalfBoard halfBoard;

    public Reservation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Guest getGuest() {
        return guest;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public int getNPersons() {
        return nPersons;
    }

    public void setNPersons(int n_persons) {
        this.nPersons = n_persons;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String credit_card_number) {
        this.creditCardNumber = credit_card_number;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date from) {
        this.dateFrom = from;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date to) {
        this.dateTo = to;
    }

    public HalfBoard getHalfBoard() {
        return halfBoard;
    }

    public void setHalfBoard(HalfBoard half_board) {
        this.halfBoard = half_board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (nPersons != that.nPersons) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(guest, that.guest)) return false;
        if (!Objects.equals(accommodation, that.accommodation))
            return false;
        if (!Objects.equals(creditCardNumber, that.creditCardNumber))
            return false;
        if (!Objects.equals(dateFrom, that.dateFrom)) return false;
        if (!Objects.equals(dateTo, that.dateTo)) return false;
        return halfBoard == that.halfBoard;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (guest != null ? guest.hashCode() : 0);
        result = 31 * result + (accommodation != null ? accommodation.hashCode() : 0);
        result = 31 * result + nPersons;
        result = 31 * result + (creditCardNumber != null ? creditCardNumber.hashCode() : 0);
        result = 31 * result + (dateFrom != null ? dateFrom.hashCode() : 0);
        result = 31 * result + (dateTo != null ? dateTo.hashCode() : 0);
        result = 31 * result + (halfBoard != null ? halfBoard.hashCode() : 0);
        return result;
    }
}
