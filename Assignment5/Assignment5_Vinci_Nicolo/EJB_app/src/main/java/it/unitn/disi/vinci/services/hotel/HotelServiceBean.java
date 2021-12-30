package it.unitn.disi.vinci.services.hotel;

import it.unitn.disi.vinci.entities.Hotel;
import it.unitn.disi.vinci.entities.Reservation;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Stateless
@Remote(HotelService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HotelServiceBean implements HotelService{

    @PersistenceContext(unitName="Default")
    private EntityManager entityManager;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Hotel readByID(final long id) throws EntityNotFoundException {
        final Hotel hotel = entityManager.find(Hotel.class, id);
        if(Objects.isNull(hotel)) {
            throw new EntityNotFoundException(String.format("Can't find Hotel with id %d", id));
        }
        return hotel;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Hotel> readByDateFromDateTo(final int nPersons, final Date dateFrom, final Date dateTo) throws EntityNotFoundException {
        final Query query = entityManager.createNativeQuery(" SELECT * FROM WEBARCH.Accommodation AS A" +
                "INNER JOIN WEBARCH.Hotel AS H" +
                "ON A.id = H.id" +
                "INNER JOIN" +
                "(SELECT R.accommodationId, SUM(R.nPersons) AS Busy FROM WEBARCH.Reservation AS R WHERE R.dateFrom > :dateTo OR R.dateTo < :dateFrom GROUP BY R.accomodationId)" +
                "AS Tmp ON Tmp.accomodationId = H.id" +
                "WHERE H.places > Tmp.Busy + :nPersons ");
        final List<Hotel> hotels = query
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .setParameter("nPersons", nPersons)
                .getResultList();
        if (hotels.isEmpty()) {
            throw new EntityNotFoundException(String.format("No Hotels available from %s to %s for %d people", dateFrom.toString(), dateTo.toString(), nPersons));
        }
        return hotels;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Hotel> readAll() throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM Hotel");
        final List<Hotel> hotels = query.getResultList();
        if (hotels.isEmpty()) {
            throw new EntityNotFoundException("There are no Hotels!");
        }
        return hotels;
    }

    @Override
    public long getPriceByID(final long id, final int nPersons, final Reservation.HalfBoard halfBoard, final Date dateFrom, final Date dateTo) throws EntityNotFoundException, EntityInputException {
        final Hotel hotel = this.readByID(id);
        long diffInMillies = Math.abs(dateTo.getTime() - dateFrom.getTime());
        long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (halfBoard.equals(Reservation.HalfBoard.Yes)) {
            return (hotel.getPrice()+ hotel.getExtraHalfBoard()) * days * nPersons;
        } else if (halfBoard.equals(Reservation.HalfBoard.No)){
            return (hotel.getPrice() * days * nPersons);
        } else {
            throw new EntityInputException("Invalid value in HalfBoard field");
        }
    }
}
