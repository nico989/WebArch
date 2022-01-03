package it.unitn.disi.vinci.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("reservationApartment")
public class ReservationApartment extends Reservation implements Serializable {

    private static final long serialVersionUID = 4662348853472983743L;

    public ReservationApartment() {
        super();
    }
}