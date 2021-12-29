package it.unitn.disi.vinci.services.reservation;

import it.unitn.disi.vinci.entities.Accommodation;
import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.entities.Reservation;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Stateless
@Remote(ReservationService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ReservationServiceBean implements ReservationService {

    @PersistenceContext(unitName="Default")
    private EntityManager entityManager;

    @Resource
    private SessionContext context;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Reservation readByID(final long id) throws EntityNotFoundException {
        final Reservation reservation = entityManager.find(Reservation.class, id);
        if(Objects.isNull(reservation)) {
            throw new EntityNotFoundException(String.format("Can't find Reservation with id %d", id));
        }
        return reservation;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Reservation readByGuest(final Guest guest) throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM Reservation R WHERE R.guest = :guest");
        final Reservation reservation = (Reservation) query
                .setParameter("guest", guest)
                .getSingleResult();
        if (Objects.isNull(reservation)) {
            throw new EntityNotFoundException(String.format("Can't find Reservation with Guest name %s and surname %s", guest.getName(), guest.getSurname()));
        }
        return reservation;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Reservation> readAll() throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM Reservation ");
        final List<Reservation> reservations = query.getResultList();
        if (reservations.isEmpty()) {
            throw new EntityNotFoundException("There are no Reservations!");
        }
        return reservations;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(final Guest guest, final Accommodation accommodation, final int nPersons, final String creditCardNumber, final Date dateFrom, final Date dateTo, final Reservation.HalfBoard halfBoard) throws EntityCRUDException, EntityInputException {
        if (Objects.isNull(guest) || Objects.isNull(accommodation) || nPersons ==0 || Objects.isNull(creditCardNumber) || Objects.isNull(dateFrom) || Objects.isNull(dateTo) || Objects.isNull(halfBoard)) {
            throw new EntityInputException("All input parameters are needed to create a Reservation");
        }
        try {
            final Reservation reservation = new Reservation();
            reservation.setGuest(guest);
            reservation.setAccommodation(accommodation);
            reservation.setNPersons(nPersons);
            reservation.setCreditCardNumber(creditCardNumber);
            reservation.setDateFrom(dateFrom);
            reservation.setDateTo(dateTo);
            reservation.setHalfBoard(halfBoard);
            this.entityManager.persist(reservation);
        } catch (final Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Reservation insertion failed due to %s", e.getMessage()));
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void deleteByID(final long id) throws EntityNotFoundException, EntityCRUDException {
        final Reservation reservation = this.readByID(id);
        try {
            entityManager.remove(reservation);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Reservation removal failed due to %s", e.getMessage()));
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public void deleteByGuest(final Guest guest) throws EntityNotFoundException, EntityCRUDException {
        final Reservation reservation = this.readByGuest(guest);
        try {
            entityManager.remove(reservation);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Reservation removal failed due to %s", e.getMessage()));
        }
    }
}
