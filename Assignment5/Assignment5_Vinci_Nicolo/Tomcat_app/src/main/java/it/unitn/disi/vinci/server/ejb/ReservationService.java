package it.unitn.disi.vinci.server.ejb;

import it.unitn.disi.vinci.server.entities.Accommodation;
import it.unitn.disi.vinci.server.entities.Guest;
import it.unitn.disi.vinci.server.entities.Reservation;
import it.unitn.disi.vinci.server.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.server.exceptions.EntityInputException;
import it.unitn.disi.vinci.server.exceptions.EntityNotFoundException;

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
