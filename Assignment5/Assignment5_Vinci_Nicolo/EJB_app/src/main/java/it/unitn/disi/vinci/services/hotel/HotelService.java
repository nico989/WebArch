package it.unitn.disi.vinci.services.hotel;

import it.unitn.disi.vinci.entities.Hotel;
import it.unitn.disi.vinci.entities.Reservation;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface HotelService {

    public Hotel readByID(final long id) throws EntityNotFoundException;
    public List<Hotel> readByDateFromDateTo(final int nPersons, final Date dateFrom, final Date dateTo) throws EntityNotFoundException;
    public List<Hotel> readAll() throws EntityNotFoundException;
    public long getPriceByID(final long id, final int nPersons, final Reservation.HalfBoard halfBoard, final Date dateFrom, final Date dateTo) throws EntityNotFoundException, EntityInputException;
}
