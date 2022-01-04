package it.unitn.disi.vinci.services.guest;

import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

@Stateless
@Remote(GuestService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GuestServiceBean implements GuestService {

    private final short MIN_LENGTH = 1;

    private final short MAX_LENGTH = 64;

    @PersistenceContext(unitName = "Default")
    private EntityManager entityManager;

    @Resource
    private SessionContext context;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Guest readById(final long id) throws EntityNotFoundException {
        final Guest guest = entityManager.find(Guest.class, id);
        if (Objects.isNull(guest)) {
            throw new EntityNotFoundException(String.format("Can't find Guest with id %d", id));
        }
        return guest;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Guest readByNameSurname(final String name, final String surname) throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM Guest AS G WHERE G.name = :name AND G.surname = :surname");
        final Guest guest = (Guest) query
                .setParameter("name", name)
                .setParameter("surname", surname)
                .getSingleResult();
        if (Objects.isNull(guest)) {
            throw new EntityNotFoundException(String.format("Can't find Guest with name %s and surname %s", name, surname));
        }
        return guest;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Guest> readAll() throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM Guest");
        final List<Guest> guests = query.getResultList();
        if (guests.isEmpty()) {
            throw new EntityNotFoundException("There are no Guests!");
        }
        return guests;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create(final String name, final String surname) throws EntityCRUDException, EntityInputException {
        if (Objects.isNull(name) || name.length() < this.MIN_LENGTH || name.length() > this.MAX_LENGTH) {
            throw new EntityInputException("Name length must be greater than 1 and less than 64 characters");
        }
        if (Objects.isNull(surname) || surname.length() < this.MIN_LENGTH || surname.length() > this.MAX_LENGTH) {
            throw new EntityInputException("Surname length must be greater than 1 and less than 64 characters");
        }
        Guest guest;
        try {
            guest = new Guest();
            guest.setName(name);
            guest.setSurname(surname);
            this.entityManager.persist(guest);
        } catch (final Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Guest insertion failed due to %s", e.getMessage()));
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deleteByID(final long id) throws EntityNotFoundException, EntityCRUDException {
        final Guest guest = this.readById(id);
        try {
            entityManager.remove(guest);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Guest removal failed due to %s", e.getMessage()));
        }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(final String name, final String surname) throws EntityNotFoundException, EntityCRUDException {
        final Guest guest = this.readByNameSurname(name, surname);
        try {
            entityManager.remove(guest);
        } catch (Exception e) {
            context.setRollbackOnly();
            throw new EntityCRUDException(String.format("Something went wrong: Guest removal failed due to %s", e.getMessage()));
        }
    }
}
