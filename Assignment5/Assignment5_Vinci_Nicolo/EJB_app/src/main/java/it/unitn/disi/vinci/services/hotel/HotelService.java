package it.unitn.disi.vinci.services.hotel;

import it.unitn.disi.vinci.entities.Hotel;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface HotelService {

    public Hotel readByID(final int id) throws EntityNotFoundException;
    public List<Hotel> readAll() throws EntityNotFoundException;
}
