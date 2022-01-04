package it.unitn.disi.vinci.app.controller;

import it.unitn.disi.vinci.app.locator.ServiceLocator;
import it.unitn.disi.vinci.app.locator.exceptions.EJBNotFound;
import it.unitn.disi.vinci.app.model.UserRequest;
import it.unitn.disi.vinci.entities.*;
import it.unitn.disi.vinci.services.apartment.ApartmentService;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;
import it.unitn.disi.vinci.services.guest.GuestService;
import it.unitn.disi.vinci.services.hotel.HotelService;
import it.unitn.disi.vinci.services.reservationApartment.ReservationApartmentService;
import it.unitn.disi.vinci.services.reservationHotel.ReservationHotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@WebServlet(name = "reservation", value = "/reservation")
public class ReservationServlet extends HttpServlet {
    public void init() {
    }

    private UserRequest getUserRequestFromSession(HttpServletRequest request) {
        return (UserRequest) request.getSession(false).getAttribute("userRequest");
    }

    private void forwardToJSP(boolean error, boolean summary, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("summary", summary);
        request.getRequestDispatcher("/reservation.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("name").isEmpty() || request.getParameter("surname").isEmpty()) {
            forwardToJSP(true, false, request, response);
        } else {
            List<ReservationHotel> reservationsHotel = null;
            try {
                final Guest guest = ServiceLocator.getInstance().ejbLookUp(GuestService.class).readByNameSurname(request.getParameter("name"), request.getParameter("surname"));
                reservationsHotel = ServiceLocator.getInstance().ejbLookUp(ReservationHotelService.class).readByGuest(guest);
            } catch (final Exception e) {
                request.setAttribute("emptyReservationsHotel", e.getMessage());
            }
            request.setAttribute("reservationsHotel", reservationsHotel);
            List<ReservationApartment> reservationsApartment = null;
            try {
                final Guest guest = ServiceLocator.getInstance().ejbLookUp(GuestService.class).readByNameSurname(request.getParameter("name"), request.getParameter("surname"));
                reservationsApartment = ServiceLocator.getInstance().ejbLookUp(ReservationApartmentService.class).readByGuest(guest);
            } catch (final Exception e) {
                request.setAttribute("emptyReservationsApartment", e.getMessage());
            }
            request.setAttribute("reservationsApartment", reservationsApartment);
            forwardToJSP(false, false, request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("creditCardNumber").isEmpty() || request.getParameter("name").isEmpty() || request.getParameter("surname").isEmpty()) {
            forwardToJSP(true, true, request, response);
        } else {
            try {
                final UserRequest userRequest = getUserRequestFromSession(request);
                ServiceLocator.getInstance().ejbLookUp(GuestService.class).create(request.getParameter("name"), request.getParameter("surname"));
                final Guest guest = ServiceLocator.getInstance().ejbLookUp(GuestService.class).readByNameSurname(request.getParameter("name"), request.getParameter("surname"));
                if (getUserRequestFromSession(request).getType().equals(UserRequest.Type.Hotel)) {
                    final Hotel hotel = ServiceLocator.getInstance().ejbLookUp(HotelService.class).readByID(userRequest.getAccommodationId());
                    ServiceLocator.getInstance().ejbLookUp(ReservationHotelService.class).create(guest, hotel, userRequest.getnPersons(), request.getParameter("creditCardNumber"), userRequest.getDateFrom(), userRequest.getDateTo(), userRequest.isExtraHalfBoard());
                } else {
                    final Apartment apartment = ServiceLocator.getInstance().ejbLookUp(ApartmentService.class).readByID(userRequest.getAccommodationId());
                    ServiceLocator.getInstance().ejbLookUp(ReservationHotelService.class).create(guest, apartment, userRequest.getnPersons(), request.getParameter("creditCardNumber"), userRequest.getDateFrom(), userRequest.getDateTo(), userRequest.isExtraHalfBoard());
                }
            } catch (final EntityNotFoundException | EJBNotFound | EntityInputException |  EntityCRUDException e) {
                request.setAttribute("exception", e.getMessage());
            }
        }
        forwardToJSP(false, true, request, response);
    }

    public void destroy() {
    }
}
