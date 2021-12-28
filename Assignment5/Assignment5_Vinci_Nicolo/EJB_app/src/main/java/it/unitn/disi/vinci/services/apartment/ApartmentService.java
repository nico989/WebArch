package it.unitn.disi.vinci.services.apartment;

import it.unitn.disi.vinci.entities.Apartment;

import java.util.List;

public interface ApartmentService {

    public Apartment readByID(final long id);
    public List<Apartment> readAll();
}
