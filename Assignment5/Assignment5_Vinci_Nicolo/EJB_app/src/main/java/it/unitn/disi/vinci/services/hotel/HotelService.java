package it.unitn.disi.vinci.services.hotel;

import it.unitn.disi.vinci.entities.Hotel;

import java.util.List;

public interface HotelService {

    public Hotel readByID(final int id);
    public List<Hotel> readAll();
}
