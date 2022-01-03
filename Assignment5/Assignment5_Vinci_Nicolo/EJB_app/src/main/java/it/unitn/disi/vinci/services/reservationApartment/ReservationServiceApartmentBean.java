package it.unitn.disi.vinci.services.reservationApartment;

import it.unitn.disi.vinci.entities.Accommodation;
import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.entities.ReservationApartment;
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
@Remote(ReservationServiceApartment.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReservationServiceApartmentBean implements ReservationServiceApartment {

    @PersistenceContext(unitName="Default")
    private EntityManager entityManager;

    @Resource
    private SessionContext context;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ReservationApartment readByID(final long id) throws EntityNotFoundException {
        final ReservationApartment reservationApartment = entityManager.find(ReservationApartment.class, id);
        if(Objects.isNull(reservationApartment)) {
            throw new EntityNotFoundException(String.format("Can't find Reservation with id %d", id));
        }
        return reservationApartment;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReservationApartment> readByGuest(final Guest guest) throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM ReservationApartment AS RA WHERE RA.guest = :guest");
        final List<ReservationApartment> reservationsApartment = query
                .setParameter("guest", guest)
                .getResultList();
        if (reservationsApartment.isEmpty()) {
            throw new EntityNotFoundException(String.format("Can't find Reservations Apartment for Guest name %s and surname %s", guest.getName(), guest.getSurname()));
        }
        return reservationsApartment;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<ReservationApartment> readAll() throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM Reservation ");
        final List<ReservationApartment> reservationsApartments = query.getResultList();
        if (reservationsApartments.isEmpty()) {
            throw new EntityNotFoundException("There are no Reservations!");
        }
        return reservationsApartments;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(final Guest guest, final Accommodation accommodation, final int nPersons, final String creditCardNumber, final Date dateFrom, final Date dateTo) throws EntityCRUDException, EntityInputException {
        if (Objects.isNull(guest) || Objects.isNull(accommodation) || nPersons ==0 || Objects.isNull(creditCardNumber) || Objects.isNull(dateFrom) || Objects.isNull(dateTo)) {
            throw new EntityInputException("All input parameters are needed to create a Reservation");
        }
        try {
            final ReservationApartment reservationApartment = new ReservationApartment();
            reservationApartment.setGuest(guest);
            reservationApartment.setAccommodation(accommodation);
            reservationApartment.setnPersons(nPersons);
            reservationApartment.setCreditCardNumber(creditCardNumber);
            reservationApartment.setDateFrom(dateFrom);
            reservationApartment.setDateTo(dateTo);
            this.entityManager.persist(reservationApartment);
        } catch (final Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Reservation insertion failed due to %s", e.getMessage()));
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteByID(final long id) throws EntityNotFoundException, EntityCRUDException {
        final ReservationApartment reservationApartment = this.readByID(id);
        try {
            entityManager.remove(reservationApartment);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Reservation removal failed due to %s", e.getMessage()));
        }
    }
}
