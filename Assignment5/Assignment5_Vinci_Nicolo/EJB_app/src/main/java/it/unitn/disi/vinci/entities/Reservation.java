package it.unitn.disi.vinci.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = -4581598877222772474L;

    public enum Half_Board {
        Yes,
        No,
        Null
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Guest guest;

    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Accommodation accommodation;

    @Column(name = "persons_number", nullable = false)
    private int n_persons;

    @Column(name = "credit_card_number", nullable = false)
    private String credit_card_number;

    @Column(name = "date_from", nullable = false)
    private Date date_from;

    @Column(name = "date_to", nullable = false)
    private Date date_to;

    @Enumerated(EnumType.STRING)
    @Column(name = "half_board", nullable = false)
    private Half_Board half_board;

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

    public int getN_persons() {
        return n_persons;
    }

    public void setN_persons(int n_persons) {
        this.n_persons = n_persons;
    }

    public String getCredit_card_number() {
        return credit_card_number;
    }

    public void setCredit_card_number(String credit_card_number) {
        this.credit_card_number = credit_card_number;
    }

    public Date getDate_from() {
        return date_from;
    }

    public void setDate_from(Date from) {
        this.date_from = from;
    }

    public Date getDate_to() {
        return date_to;
    }

    public void setDate_to(Date to) {
        this.date_to = to;
    }

    public Half_Board getHalf_board() {
        return half_board;
    }

    public void setHalf_board(Half_Board half_board) {
        this.half_board = half_board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (n_persons != that.n_persons) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(guest, that.guest)) return false;
        if (!Objects.equals(accommodation, that.accommodation))
            return false;
        if (!Objects.equals(credit_card_number, that.credit_card_number))
            return false;
        if (!Objects.equals(date_from, that.date_from)) return false;
        if (!Objects.equals(date_to, that.date_to)) return false;
        return half_board == that.half_board;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (guest != null ? guest.hashCode() : 0);
        result = 31 * result + (accommodation != null ? accommodation.hashCode() : 0);
        result = 31 * result + n_persons;
        result = 31 * result + (credit_card_number != null ? credit_card_number.hashCode() : 0);
        result = 31 * result + (date_from != null ? date_from.hashCode() : 0);
        result = 31 * result + (date_to != null ? date_to.hashCode() : 0);
        result = 31 * result + (half_board != null ? half_board.hashCode() : 0);
        return result;
    }
}
