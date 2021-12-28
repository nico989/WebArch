package it.unitn.disi.vinci.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "reservation")
public class Reservation implements Serializable {

    private static final long serialVersionUID = -4581598877222772474L;

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
    private int credit_card_number;

    @Column(name = "from", nullable = false)
    private Date from;

    @Column(name = "to", nullable = false)
    private Date to;

    @Column(name = "half_board")
    private boolean half_board;

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

    public int getCredit_card_number() {
        return credit_card_number;
    }

    public void setCredit_card_number(int credit_card_number) {
        this.credit_card_number = credit_card_number;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public boolean isHalf_board() {
        return half_board;
    }

    public void setHalf_board(boolean half_board) {
        this.half_board = half_board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reservation that = (Reservation) o;

        if (n_persons != that.n_persons) return false;
        if (credit_card_number != that.credit_card_number) return false;
        if (half_board != that.half_board) return false;
        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(guest, that.guest)) return false;
        if (!Objects.equals(accommodation, that.accommodation))
            return false;
        if (!Objects.equals(from, that.from)) return false;
        return Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (guest != null ? guest.hashCode() : 0);
        result = 31 * result + (accommodation != null ? accommodation.hashCode() : 0);
        result = 31 * result + n_persons;
        result = 31 * result + credit_card_number;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (half_board ? 1 : 0);
        return result;
    }
}