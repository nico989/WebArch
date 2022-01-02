package it.unitn.disi.vinci.services.reservationHotel;

import it.unitn.disi.vinci.entities.Accommodation;
import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.entities.ReservationHotel;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface ReservationHotelService {

    public ReservationHotel readByID(final long id) throws EntityNotFoundException;
    public ReservationHotel readByGuest(final Guest guest) throws EntityNotFoundException;
    public List<ReservationHotel> readAll() throws EntityNotFoundException;
    public void create(final Guest guest, final Accommodation accommodation, final int nPersons, final String creditCardNumber, final Date dateFrom, final Date dateTo, final boolean halfBoard) throws EntityCRUDException, EntityInputException;
    public void deleteByID(final long id) throws EntityNotFoundException, EntityCRUDException;
}
