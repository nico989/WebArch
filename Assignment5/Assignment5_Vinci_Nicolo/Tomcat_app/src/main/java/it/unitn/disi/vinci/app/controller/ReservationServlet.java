package it.unitn.disi.vinci.app.controller;

import it.unitn.disi.vinci.app.locator.ServiceLocator;
import it.unitn.disi.vinci.app.locator.exceptions.EJBNotFound;
import it.unitn.disi.vinci.app.model.UserRequest;
import it.unitn.disi.vinci.entities.Accommodation;
import it.unitn.disi.vinci.entities.Apartment;
import it.unitn.disi.vinci.entities.Guest;
import it.unitn.disi.vinci.entities.Hotel;
import it.unitn.disi.vinci.services.apartment.ApartmentService;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;
import it.unitn.disi.vinci.services.hotel.HotelService;
import it.unitn.disi.vinci.services.reservationApartment.ReservationApartmentService;
import it.unitn.disi.vinci.services.reservationHotel.ReservationHotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "reservation", value = "/reservation")
public class ReservationServlet extends HttpServlet {
    public void init() {
    }

    private UserRequest getUserRequestFromSession(HttpServletRequest request) {
        return (UserRequest) request.getSession(false).getAttribute("userRequest");
    }

    private void forwardToJSP(boolean error, Accommodation accommodation, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("accommodation", accommodation);
        request.getRequestDispatcher("/reservation.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("creditCardNumber").isEmpty() || request.getParameter("name").isEmpty() || request.getParameter("surname").isEmpty()) {
            forwardToJSP(true, null, request, response);
        } else {
            final UserRequest userRequest = getUserRequestFromSession(request);
            final Guest guest = new Guest();
            guest.setName(request.getParameter("name"));
            guest.setSurname(request.getParameter("surname"));
            if (userRequest.getType().equals(UserRequest.Type.Hotel)){
                try {
                    final Hotel hotel = ServiceLocator.getInstance().ejbLookUp(HotelService.class).readByID(userRequest.getAccommodationId());
                    ServiceLocator.getInstance().ejbLookUp(ReservationHotelService.class).create(guest, hotel, userRequest.getnPersons(), request.getParameter("creditCardNumber"), userRequest.getDateFrom(), userRequest.getDateTo(), userRequest.isExtraHalfBoard());
                    forwardToJSP(false, hotel, request, response);
                } catch (EntityCRUDException | EntityInputException | EJBNotFound | EntityNotFoundException e) {
                    forwardToJSP(true, null, request, response);
                }
            } else {
                try {
                    final Apartment apartment = ServiceLocator.getInstance().ejbLookUp(ApartmentService.class).readByID(userRequest.getAccommodationId());
                    ServiceLocator.getInstance().ejbLookUp(ReservationApartmentService.class).create(guest, apartment, userRequest.getnPersons(), request.getParameter("creditCardNumber"), userRequest.getDateFrom(), userRequest.getDateTo());
                    forwardToJSP(false, apartment, request, response);
                } catch (EntityCRUDException | EntityInputException | EJBNotFound | EntityNotFoundException e) {
                    forwardToJSP(true, null, request, response);
                }
            }
        }
    }

    public void destroy() {
    }
}
