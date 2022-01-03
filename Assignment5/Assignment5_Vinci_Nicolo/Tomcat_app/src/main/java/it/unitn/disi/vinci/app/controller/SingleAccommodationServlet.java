package it.unitn.disi.vinci.app.controller;

import it.unitn.disi.vinci.app.locator.ServiceLocator;
import it.unitn.disi.vinci.app.locator.exceptions.EJBNotFound;
import it.unitn.disi.vinci.app.model.UserRequest;
import it.unitn.disi.vinci.services.apartment.ApartmentService;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;
import it.unitn.disi.vinci.services.hotel.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet(name = "singleAccommodationHotel", value = "/singleAccommodationHotel")
public class SingleAccommodationServlet extends HttpServlet {
    public void init() {
    }

    private UserRequest getUserRequestFromSession(HttpServletRequest request) {
        return (UserRequest) request.getSession(false).getAttribute("userRequest");
    }

    private void forwardToJSP(boolean error, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.getRequestDispatcher("/singleAccommodation.jsp").forward(request, response);
    }

    private void handleHotel(UserRequest.Type type, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Objects.isNull(request.getParameter("hotelId"))) {
            forwardToJSP(true, request, response);
        } else {
            final int hotelId = Integer.parseInt(request.getParameter("hotelId"));
            final boolean extraHalfBoard = Objects.nonNull(request.getParameter("extraHalfBoard")) && request.getParameter("extraHalfBoard").equals("chosen");
            final UserRequest userRequest = getUserRequestFromSession(request);
            userRequest.setAccommodationId(hotelId);
            userRequest.setExtraHalfBoard(extraHalfBoard);
            userRequest.setType(type);
            try {
                final long totPrice = ServiceLocator.getInstance().ejbLookUp(HotelService.class).getPriceByID(userRequest.getAccommodationId(), userRequest.getnPersons(), userRequest.isExtraHalfBoard(), userRequest.getDateFrom(), userRequest.getDateTo());
                request.setAttribute("totPrice", totPrice);
                forwardToJSP(false, request, response);
            } catch (final EJBNotFound | EntityNotFoundException e) {
                forwardToJSP(true, request, response);
            }
        }
    }

    private void handleApartment(UserRequest.Type type, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Objects.isNull(request.getParameter("apartmentId"))) {
            forwardToJSP(true, request, response);
        } else {
            final int apartmentId = Integer.parseInt(request.getParameter("apartmentId"));
            final UserRequest userRequest = getUserRequestFromSession(request);
            userRequest.setAccommodationId(apartmentId);
            userRequest.setType(type);
            try {
                final long totPrice = ServiceLocator.getInstance().ejbLookUp(ApartmentService.class).getPriceByID(userRequest.getAccommodationId(), userRequest.getDateFrom(), userRequest.getDateTo());
                request.setAttribute("totPrice", totPrice);
                forwardToJSP(false, request, response);
            } catch (final EJBNotFound | EntityNotFoundException e) {
                forwardToJSP(true, request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("type").equals("hotel")) {
            handleHotel(UserRequest.Type.Hotel, request, response);
        } else {
            handleApartment(UserRequest.Type.Apartment, request, response);
        }
    }

    public void destroy() {
    }
}
