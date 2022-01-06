package it.unitn.disi.vinci.services.routine;

import it.unitn.disi.vinci.entities.*;
import it.unitn.disi.vinci.services.apartment.ApartmentService;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;
import it.unitn.disi.vinci.services.guest.GuestService;
import it.unitn.disi.vinci.services.hotel.HotelService;
import it.unitn.disi.vinci.services.reservationApartment.ReservationApartmentService;
import it.unitn.disi.vinci.services.reservationHotel.ReservationHotelService;

import javax.ejb.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Stateless
@Remote(RoutineService.class)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class RoutineServiceBean implements RoutineService {

    @EJB
    GuestService guestService;

    @EJB
    HotelService hotelService;

    @EJB
    ApartmentService apartmentService;

    @EJB
    ReservationHotelService reservationHotelService;

    @EJB
    ReservationApartmentService reservationApartmentService;

    @Override
    public void routineHotel() throws EntityNotFoundException, ParseException, EntityCRUDException, EntityInputException {
        final Guest guest = guestService.readByNameSurname("Mock", "Guest");
        final Random random = new Random();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final List<Hotel> hotels = hotelService.readAll();
        int randomPersons;
        for (Hotel hotel : hotels) {
            final int min = (hotel.getPlaces() * 90) / 100;
            final int max = hotel.getPlaces() + 1 ;
            for (int i = 1; i <= 28; i++) {
                randomPersons = random.nextInt((max - min)) + min;
                final Date date = simpleDateFormat.parse((i < 10 ? "0" + i : "" + i) + "/02/2022");
                reservationHotelService.create(guest, hotel, randomPersons, "0000 0000 0000 0000", date, date, false);
            }
        }
    }

    @Override
    public void routineApartment() throws EntityNotFoundException, ParseException, EntityCRUDException, EntityInputException {
        final Guest guest = guestService.readByNameSurname("Mock", "Guest");
        final Random random = new Random();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        final Set<Integer> alreadyUsed = new HashSet<>();
        final List<Apartment> apartments = apartmentService.readAll();
        int randomDay;
        for (Apartment apartment : apartments) {
            while(alreadyUsed.size() < 4 ) {
                randomDay = random.nextInt((29 - 1)) + 1;
                if (alreadyUsed.add(randomDay)) {
                    final Date date = simpleDateFormat.parse((randomDay < 10 ? "0" + randomDay : "" + randomDay) + "/02/2022");
                    reservationApartmentService.create(guest, apartment, apartment.getMaxPersons(), "0000 0000 0000 0000", date, date);
                }
            }
            alreadyUsed.clear();
        }
    }

    @Override
    public void cleanAll() throws EntityNotFoundException, EntityCRUDException {
        final List<ReservationHotel> reservationsHotel = reservationHotelService.readAll();
        for (ReservationHotel reservationHotel: reservationsHotel) {
            reservationHotelService.deleteByID(reservationHotel.getId());
        }
        final List<ReservationApartment> reservationsApartment = reservationApartmentService.readAll();
        for (ReservationApartment reservationApartment: reservationsApartment) {
            reservationApartmentService.deleteByID(reservationApartment.getId());
        }
    }

}
