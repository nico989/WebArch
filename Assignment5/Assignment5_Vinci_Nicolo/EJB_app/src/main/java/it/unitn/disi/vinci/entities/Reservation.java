package it.unitn.disi.vinci.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
    private int persons_number;

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

    public int getPersons_number() {
        return persons_number;
    }

    public void setPersons_number(int persons_number) {
        this.persons_number = persons_number;
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
}
