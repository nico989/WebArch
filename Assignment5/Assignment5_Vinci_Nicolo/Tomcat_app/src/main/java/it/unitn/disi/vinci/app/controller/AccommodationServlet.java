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

    private void forwardToJSP(boolean error, boolean empty, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", error);
        request.setAttribute("empty", empty);
        request.getRequestDispatcher("/accommodation.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String IDateFrom = request.getParameter("dateFrom");
        final String IDateTo = request.getParameter("dateTo");
        final String INPersons = request.getParameter("nPersons");

        try {
            if (IDateFrom.isEmpty() || IDateTo.isEmpty() || INPersons.isEmpty()) {
                forwardToJSP(true, false, request, response);
            } else {
                final int nPersons = Integer.parseInt(INPersons);
                final Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(IDateFrom);
                final Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(IDateTo);

                final List<Hotel> hotels = ServiceLocator.getInstance().ejbLookUp(HotelService.class).readByDateFromDateTo(nPersons, dateFrom, dateTo);
                final List<Apartment> apartments = ServiceLocator.getInstance().ejbLookUp(ApartmentService.class).readByDateFromDateTo(dateFrom, dateTo);

                createUserRequestBeanInSession(request, dateFrom, dateTo, nPersons);

                request.setAttribute("hotels", hotels);
                request.setAttribute("apartments", apartments);

                forwardToJSP(false, false, request, response);
            }
        } catch (final EntityNotFoundException e) {
            forwardToJSP(false, true, request, response);
        } catch (final EJBNotFound | ParseException e) {
            forwardToJSP(true, false, request, response);
        }
    }

    public void destroy() {
    }
}
