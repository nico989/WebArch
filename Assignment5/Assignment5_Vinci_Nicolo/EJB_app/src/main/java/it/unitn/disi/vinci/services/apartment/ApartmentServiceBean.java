package it.unitn.disi.vinci.services.apartment;

import it.unitn.disi.vinci.entities.Apartment;
import it.unitn.disi.vinci.entities.Reservation;
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
@Remote(ApartmentService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ApartmentServiceBean implements ApartmentService {

    @PersistenceContext(unitName = "Default")
    private EntityManager entityManager;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Apartment readByID(long id) throws EntityNotFoundException {
        final Apartment apartment = entityManager.find(Apartment.class, id);
        if (Objects.isNull(apartment)) {
            throw new EntityNotFoundException(String.format("Can't find Apartment with id %d", id));
        }
        return apartment;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Apartment> readByDateFromDateTo(final Date dateFrom, final Date dateTo) throws EntityNotFoundException {
        /*final List<Apartment> apartments = this.readAll();

        final Query queryReservationsApartment = entityManager.createQuery("FROM ReservationApartment");
        final List<ReservationApartment> reservationsApartment = queryReservationsApartment.getResultList();

        final List<ReservationApartment> filteredReservationsApartment = reservationsApartment.stream().filter(reservation ->
                !(reservation.getDateFrom().after(dateTo) || reservation.getDateTo().before(dateFrom))
                ).collect(Collectors.toList());

        System.out.println("FILTERED APARTMENTS");
        for (ReservationApartment reservationApartment: filteredReservationsApartment) {
            System.out.println(reservationApartment.getAccommodation().getName());
            System.out.println(reservationApartment.getDateFrom());
            System.out.println(reservationApartment.getDateTo());
        }

        if (!filteredReservationsApartment.isEmpty()) {
            for (ReservationApartment reservationApartment : filteredReservationsApartment) {
                final Apartment apartment = (Apartment) reservationApartment.getAccommodation();
                apartments.remove(apartment);
            }
        }

        if (apartments.isEmpty()) {
            throw new EntityNotFoundException(String.format("No Apartments available from %s to %s", dateFrom.toString(), dateTo.toString()));
        }

        System.out.println("AVAILABLE APARTMENTS");
        for (Apartment apartment: apartments) {
            System.out.println(apartment.getId());
            System.out.println(apartment.getName());
        }*/

        final Query queryReservationsApartment = entityManager.createQuery("FROM Apartment AS A WHERE A.id NOT IN(" +
                "SELECT RA.accommodation.id FROM ReservationApartment AS RA WHERE NOT (RA.dateFrom > :dateTo OR RA.dateTo < :dateFrom)" +
                ")");
        final List<Apartment> apartments = queryReservationsApartment
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .getResultList();

        System.out.println("AVAILABLE APARTMENTS");
        for (Apartment apartment: apartments) {
            System.out.println(apartment.getId());
            System.out.println(apartment.getName());
        }

        return apartments;
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

    @Override
    public long getPriceByID(final long id, Date dateFrom, Date dateTo) throws EntityNotFoundException {
        final Apartment apartment = this.readByID(id);
        long diffInMillies = Math.abs(dateTo.getTime() - dateFrom.getTime());
        long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return (apartment.getPrice() * days) + apartment.getFinalCleaning();
    }
}
