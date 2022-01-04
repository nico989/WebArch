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

@WebServlet(name = "singleAccommodation", value = "/singleAccommodation")
public class SingleAccommodationServlet extends HttpServlet {
    public void init() {
    }

    private UserRequest getUserRequestFromSession(HttpServletRequest request) {
        return (UserRequest) request.getSession(false).getAttribute("userRequest");
    }

    private void forwardToJSP(String exception, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("exception", exception);
        request.getRequestDispatcher("/singleAccommodation.jsp").forward(request, response);
    }

    private void handleHotel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Objects.isNull(request.getParameter("hotelId"))) {
            forwardToJSP(null, request, response);
        } else {
            final int hotelId = Integer.parseInt(request.getParameter("hotelId"));
            final boolean extraHalfBoard = Objects.nonNull(request.getParameter("extraHalfBoard")) && request.getParameter("extraHalfBoard").equals("chosen");
            final UserRequest userRequest = getUserRequestFromSession(request);
            userRequest.setAccommodationId(hotelId);
            userRequest.setExtraHalfBoard(extraHalfBoard);
            userRequest.setType(UserRequest.Type.Hotel);
            try {
                final long totPrice = ServiceLocator.getInstance().ejbLookUp(HotelService.class).getPriceByID(userRequest.getAccommodationId(), userRequest.getnPersons(), userRequest.isExtraHalfBoard(), userRequest.getDateFrom(), userRequest.getDateTo());
                request.setAttribute("totPrice", totPrice);
                forwardToJSP(null, request, response);
            } catch (final EJBNotFound | EntityNotFoundException e) {
                forwardToJSP(e.getMessage(), request, response);
            }
        }
    }

    private void handleApartment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(Objects.isNull(request.getParameter("apartmentId"))) {
            forwardToJSP(null, request, response);
        } else {
            final int apartmentId = Integer.parseInt(request.getParameter("apartmentId"));
            final UserRequest userRequest = getUserRequestFromSession(request);
            userRequest.setAccommodationId(apartmentId);
            userRequest.setType(UserRequest.Type.Apartment);
            try {
                final long totPrice = ServiceLocator.getInstance().ejbLookUp(ApartmentService.class).getPriceByID(userRequest.getAccommodationId(), userRequest.getDateFrom(), userRequest.getDateTo());
                request.setAttribute("totPrice", totPrice);
                forwardToJSP(null, request, response);
            } catch (final EJBNotFound | EntityNotFoundException e) {
                forwardToJSP(e.getMessage(), request, response);
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("type").equals("hotel")) {
            handleHotel(request, response);
        } else {
            handleApartment(request, response);
        }
    }

    public void destroy() {
    }
}
