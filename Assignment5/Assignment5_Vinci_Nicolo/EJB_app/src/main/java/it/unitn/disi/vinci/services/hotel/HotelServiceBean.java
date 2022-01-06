package it.unitn.disi.vinci.services.hotel;

import it.unitn.disi.vinci.entities.Hotel;
import it.unitn.disi.vinci.entities.ReservationHotel;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Stateless
@Remote(HotelService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class HotelServiceBean implements HotelService {

    @PersistenceContext(unitName = "Default")
    private EntityManager entityManager;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public Hotel readByID(final long id) throws EntityNotFoundException {
        final Hotel hotel = entityManager.find(Hotel.class, id);
        if (Objects.isNull(hotel)) {
            throw new EntityNotFoundException(String.format("Can't find Hotel with id %d", id));
        }
        return hotel;
    }

    private List<Date> getDaysBetweenDates(final Date dateFrom, final Date dateTo) {
        final List<Date> dates = new ArrayList<>();
        final Calendar calendar = new GregorianCalendar();
        calendar.setTime(dateFrom);
        while (calendar.getTime().before(dateTo)) {
            dates.add(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        System.out.println(calendar.getTime());
        dates.add(calendar.getTime());
        return dates;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<Hotel> readByDateFromDateTo(final int nPersons, final Date dateFrom, final Date dateTo) throws EntityNotFoundException {
        final List<Hotel> hotels = this.readAll();
        final Query queryReservationsHotel = entityManager.createQuery("FROM ReservationHotel AS RH WHERE NOT (RH.dateFrom > :dateTo OR RH.dateTo < :dateFrom)");
        final List<ReservationHotel> reservationsHotel = queryReservationsHotel
                .setParameter("dateFrom", dateFrom)
                .setParameter("dateTo", dateTo)
                .getResultList();
        final List<Hotel> filteredHotels = new ArrayList<>(hotels);
        final List<Date> dates = getDaysBetweenDates(dateFrom, dateTo);
        for (Hotel hotel : hotels) {
            int totPersons = 0;
            for (Date date : dates) {
                for (ReservationHotel reservationHotel : reservationsHotel) {
                    if (hotel.equals(reservationHotel.getAccommodation()) && !date.before(reservationHotel.getDateFrom()) && !date.after(reservationHotel.getDateTo())) {
                        totPersons += reservationHotel.getnPersons();
                    }
                }
                if (totPersons + nPersons > hotel.getPlaces()) {
                    filteredHotels.remove(hotel);
                    break;
                }
            }
            if (totPersons + nPersons > hotel.getPlaces()) {
                filteredHotels.remove(hotel);
            }
        }
        if (filteredHotels.isEmpty()) {
            throw new EntityNotFoundException(String.format("No Hotels available from %s to %s for %d persons", dateFrom.toString(), dateTo.toString(), nPersons));
        }
        return filteredHotels;
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
    public long getPriceByID(final long id, final int nPersons, final boolean halfBoard, final Date dateFrom, final Date dateTo) throws EntityNotFoundException {
        final Hotel hotel = this.readByID(id);
        long diffInMillies = Math.abs(dateTo.getTime() - dateFrom.getTime());
        long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        if (halfBoard) {
            return (hotel.getPrice() + hotel.getExtraHalfBoard()) * days * nPersons;
        } else {
            return (hotel.getPrice() * days * nPersons);
        }
    }
}
