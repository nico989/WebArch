package it.unitn.disi.vinci.services.apartment;

import it.unitn.disi.vinci.entities.Apartment;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Objects;

@Stateless
@Remote(ApartmentService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ApartmentServiceBean implements ApartmentService{

    @PersistenceContext(unitName="Default")
    private EntityManager entityManager;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Apartment readByID(long id) throws EntityNotFoundException {
        final Apartment apartment = entityManager.find(Apartment.class, id);
        if(Objects.isNull(apartment)) {
            throw new EntityNotFoundException(String.format("Can't find Apartment with id %d", id));
        }
        return apartment;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Apartment> readAll() throws EntityNotFoundException {
        final Query query = entityManager.createQuery("FROM Apartment ");
        final List<Apartment> apartments = query.getResultList();
        if (apartments.isEmpty()) {
            throw new EntityNotFoundException("There are no Apartments!");
        }
        return apartments;
    }
}
