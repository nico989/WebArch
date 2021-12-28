package it.unitn.disi.vinci.services.reservation;

import it.unitn.disi.vinci.entities.Accommodation;
import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.entities.Reservation;

import java.util.Date;
import java.util.List;

public interface ReservationService {

    public Reservation readByGuest(final Guest guest);
    public List<Reservation> readAllByGuest(final Guest guest);
    public List<Reservation> readAll();
    public boolean create(final Guest guest, final Accommodation accommodation, final int n_persons, final int credit_card_number, final Date from, final Date to, final boolean half_board);
    public boolean deleteByGuest(final Guest guest);
}
