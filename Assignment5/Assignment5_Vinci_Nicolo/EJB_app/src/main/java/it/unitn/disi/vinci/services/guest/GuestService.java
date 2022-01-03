package it.unitn.disi.vinci.services.guest;

import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;

import java.util.List;

public interface GuestService {

    public Guest readById(final long id) throws EntityNotFoundException;
    public Guest readByNameSurname(final String name, final String surname) throws EntityNotFoundException;
    public List<Guest> readAll() throws EntityNotFoundException;
    public void create(final String name, final String surname) throws EntityCRUDException, EntityInputException, EntityNotFoundException;
    public void deleteByID(final long id) throws EntityNotFoundException, EntityCRUDException;
    public void delete(final String name, final String surname) throws EntityNotFoundException, EntityCRUDException;
}
