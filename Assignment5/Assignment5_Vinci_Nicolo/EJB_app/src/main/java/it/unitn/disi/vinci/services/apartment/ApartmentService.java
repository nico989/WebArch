package it.unitn.disi.vinci.services.apartment;

import it.unitn.disi.vinci.entities.Apartment;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface ApartmentService {

    public Apartment readByID(final long id) throws EntityNotFoundException;
    public List<Apartment> readAll() throws EntityNotFoundException;
}
