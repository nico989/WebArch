package it.unitn.disi.vinci.services.hotel;

import it.unitn.disi.vinci.entities.Hotel;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

@Stateless
@Remote(HotelService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HotelServiceBean implements HotelService{

    @PersistenceContext(unitName="Default")
    private EntityManager entityManager;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Hotel readByID(final int id) {
        final Hotel hotel = entityManager.find(Hotel.class, id);
        if(Objects.isNull(hotel)) {
            throw new EntityNotFoundException(String.format("Can't find Hotel with id %d", id));
        }
        return hotel;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Hotel> readAll() {
        final Query query = entityManager.createQuery("FROM Hotel");
        final List<Hotel> hotels = query.getResultList();
        if (hotels.isEmpty()) {
            throw new EntityNotFoundException("There are no Hotels!");
        }
        return hotels;
    }
}
