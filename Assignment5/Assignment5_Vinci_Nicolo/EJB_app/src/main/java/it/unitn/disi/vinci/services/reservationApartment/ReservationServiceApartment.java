package it.unitn.disi.vinci.services.reservationApartment;

import it.unitn.disi.vinci.entities.Accommodation;
import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.entities.ReservationApartment;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface ReservationServiceApartment {

    public ReservationApartment readByID(final long id) throws EntityNotFoundException;
    public List<ReservationApartment> readByGuest(final Guest guest) throws EntityNotFoundException;
    public List<ReservationApartment> readAll() throws EntityNotFoundException;
    public void create(final Guest guest, final Accommodation accommodation, final int nPersons, final String creditCardNumber, final Date dateFrom, final Date dateTo) throws EntityCRUDException, EntityInputException;
    public void deleteByID(final long id) throws EntityNotFoundException, EntityCRUDException;
}
