package it.unitn.disi.vinci.services.reservationHotel;


import it.unitn.disi.vinci.entities.Accommodation;
import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.entities.ReservationHotel;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Stateless
@Remote(ReservationHotelService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReservationHotelServiceBean implements ReservationHotelService {

    @PersistenceContext(unitName="Default")
    private EntityManager entityManager;

    @Resource
    private SessionContext context;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReservationHotel readByID(long id) throws EntityNotFoundException {
        final ReservationHotel reservationHotel = entityManager.find(ReservationHotel.class, id);
        if(Objects.isNull(reservationHotel)) {
            throw new EntityNotFoundException(String.format("Can't find Reservation Hotel with id %d", id));
        }
        return reservationHotel;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReservationHotel> readByGuest(Guest guest) throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM ReservationHotel RH WHERE RH.guest = :guest");
        final List<ReservationHotel> reservationsHotel = query
                .setParameter("guest", guest)
                .getResultList();
        if (reservationsHotel.isEmpty()) {
            throw new EntityNotFoundException(String.format("Can't find Reservations Hotel for Guest name %s and surname %s", guest.getName(), guest.getSurname()));
        }
        return reservationsHotel;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReservationHotel> readAll() throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM ReservationHotel ");
        final List<ReservationHotel> reservationHotels = query.getResultList();
        if (reservationHotels.isEmpty()) {
            throw new EntityNotFoundException("There are no Reservations!");
        }
        return reservationHotels;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(Guest guest, Accommodation accommodation, int nPersons, String creditCardNumber, Date dateFrom, Date dateTo, boolean halfBoard) throws EntityCRUDException, EntityInputException {
        if (Objects.isNull(guest) || Objects.isNull(accommodation) || nPersons ==0 || Objects.isNull(creditCardNumber) || Objects.isNull(dateFrom) || Objects.isNull(dateTo) || Objects.isNull(halfBoard)) {
            throw new EntityInputException("All input parameters are needed to create a Reservation");
        }
        try {
            final ReservationHotel reservationHotel = new ReservationHotel();
            reservationHotel.setGuest(guest);
            reservationHotel.setAccommodation(accommodation);
            reservationHotel.setnPersons(nPersons);
            reservationHotel.setCreditCardNumber(creditCardNumber);
            reservationHotel.setDateFrom(dateFrom);
            reservationHotel.setDateTo(dateTo);
            reservationHotel.setHalfBoard(halfBoard);
            this.entityManager.persist(reservationHotel);
        } catch (final Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Reservation Hotel insertion failed due to %s", e.getMessage()));
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteByID(long id) throws EntityNotFoundException, EntityCRUDException {
        final ReservationHotel reservationHotel = this.readByID(id);
        try {
            entityManager.remove(reservationHotel);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Reservation Hotel removal failed due to %s", e.getMessage()));
        }
    }
}
