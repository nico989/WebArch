package it.unitn.disi.vinci.services.reservation;

import it.unitn.disi.vinci.entities.Accommodation;
import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.entities.Reservation;
import it.unitn.disi.vinci.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.exceptions.EntityInputException;
import it.unitn.disi.vinci.exceptions.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface ReservationService {

    public Reservation readByID(final long id) throws EntityNotFoundException;
    public Reservation readByGuest(final Guest guest) throws EntityNotFoundException;
    public List<Reservation> readAll() throws EntityNotFoundException;
    public void create(final Guest guest, final Accommodation accommodation, final int nPersons, final String creditCardNumber, final Date dateFrom, final Date dateTo, final Reservation.HalfBoard halfBoard) throws EntityCRUDException, EntityInputException;
    public void deleteByID(final long id) throws EntityNotFoundException, EntityCRUDException;
    public void deleteByGuest(final Guest guest) throws EntityNotFoundException, EntityCRUDException;
}
