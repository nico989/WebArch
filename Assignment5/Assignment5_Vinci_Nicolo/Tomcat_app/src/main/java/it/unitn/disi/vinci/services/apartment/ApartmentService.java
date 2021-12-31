package it.unitn.disi.vinci.services.apartment;

import it.unitn.disi.vinci.entities.Apartment;
import it.unitn.disi.vinci.exceptions.EntityNotFoundException;

import java.util.Date;
import java.util.List;

public interface ApartmentService {

    public Apartment readByID(final long id) throws EntityNotFoundException;
    public List<Apartment> readByDateFromDateTo(final Date dateFrom, final Date dateTo) throws EntityNotFoundException;
    public List<Apartment> readAll() throws EntityNotFoundException;
    public long getPriceByID(final long id, final Date dateFrom, final Date dateTo) throws EntityNotFoundException;
}
