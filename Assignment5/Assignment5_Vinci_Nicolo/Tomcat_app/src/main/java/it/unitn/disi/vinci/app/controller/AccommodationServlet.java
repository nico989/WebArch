package it.unitn.disi.vinci.app.controller;

import it.unitn.disi.vinci.app.locator.ServiceLocator;
import it.unitn.disi.vinci.app.locator.exceptions.EJBNotFound;
import it.unitn.disi.vinci.app.model.UserRequest;
import it.unitn.disi.vinci.entities.Apartment;
import it.unitn.disi.vinci.entities.Hotel;
import it.unitn.disi.vinci.services.apartment.ApartmentService;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;
import it.unitn.disi.vinci.services.hotel.HotelService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "accommodation", value = "/accommodation")
public class AccommodationServlet extends HttpServlet {

    public void init() {
    }

    private void createUserRequestBeanInSession(HttpServletRequest request, Date dateFrom, Date dateTo, int nPersons) {
        HttpSession session = request.getSession(true);
        if (Objects.isNull(session.getAttribute("userRequest"))) {
            session.setAttribute("userRequest", new UserRequest());
        }
        UserRequest userRequest = (UserRequest) session.getAttribute("userRequest");
        userRequest.setDateFrom(dateFrom);
        userRequest.setDateTo(dateTo);
        userRequest.setnPersons(nPersons);
    }

    private void forwardToJSP(boolean error, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.getRequestDispatcher("/accommodation.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String IDateFrom = request.getParameter("dateFrom");
        final String IDateTo = request.getParameter("dateTo");
        final String INPersons = request.getParameter("nPersons");
        if (IDateFrom.isEmpty() || IDateTo.isEmpty() || INPersons.isEmpty()) {
            forwardToJSP(true, request, response);
        } else {
            int nPersons;
            Date dateFrom;
            Date dateTo;
            try {
                nPersons = Integer.parseInt(INPersons);
                dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(IDateFrom);
                dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(IDateTo);
                createUserRequestBeanInSession(request, dateFrom, dateTo, nPersons);
                List<Hotel> hotels = null;
                try {
                    hotels = ServiceLocator.getInstance().ejbLookUp(HotelService.class).readByDateFromDateTo(nPersons, dateFrom, dateTo);
                } catch (final EJBNotFound | EntityNotFoundException e) {
                    request.setAttribute("emptyHotels", e.getMessage());
                }
                request.setAttribute("hotels", hotels);
                List<Apartment> apartments = null;
                try {
                    apartments = ServiceLocator.getInstance().ejbLookUp(ApartmentService.class).readByDateFromDateTo(nPersons, dateFrom, dateTo);
                } catch (final EntityNotFoundException | EJBNotFound e) {
                    request.setAttribute("emptyApartments", e.getMessage());
                }
                request.setAttribute("apartments", apartments);
                forwardToJSP(false, request, response);
            } catch (ParseException e) {
                forwardToJSP(true, request, response);
            }
        }
    }

    public void destroy() {
    }
}
