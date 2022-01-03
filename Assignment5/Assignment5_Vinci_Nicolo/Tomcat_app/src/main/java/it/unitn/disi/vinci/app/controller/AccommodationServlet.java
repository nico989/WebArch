package it.unitn.disi.vinci.app.controller;

import it.unitn.disi.vinci.app.locator.ServiceLocator;
import it.unitn.disi.vinci.app.locator.exceptions.EJBNotFound;
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
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "accommodation", value = "/accommodation")
public class AccommodationServlet extends HttpServlet {

    public void init() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String name = request.getParameter("name");
        final String surname = request.getParameter("surname");
        final String IDateFrom = request.getParameter("dateFrom");
        final String IDateTo = request.getParameter("dateTo");
        final String INPersons = request.getParameter("nPersons");

        if (name.isEmpty() || surname.isEmpty() || IDateFrom.isEmpty() || IDateTo.isEmpty() || INPersons.isEmpty()) {
            request.setAttribute("error", true);
            request.getRequestDispatcher("/accommodation.jsp").forward(request, response);
        }

        try {
            final int nPersons = Integer.parseInt(INPersons);
            final Date dateFrom = new SimpleDateFormat("dd/MM/yyyy").parse(IDateFrom);
            final Date dateTo = new SimpleDateFormat("dd/MM/yyyy").parse(IDateTo);
            System.out.println(dateFrom);
            System.out.println(dateTo);
            System.out.println(nPersons);

            //final List<Apartment> apartments = ServiceLocator.getInstance().ejbLookUp(ApartmentService.class).readByDateFromDateTo(dateFrom, dateTo);
            final List<Hotel> hotels = ServiceLocator.getInstance().ejbLookUp(HotelService.class).readByDateFromDateTo(nPersons, dateFrom, dateTo);

            for (Hotel hotel : hotels) {
                System.out.println(hotel.getName());
                System.out.println(hotel.getPlaces());
            }

            request.setAttribute("error", false);
            request.getRequestDispatcher("/accommodation.jsp").forward(request, response);
        } catch (final EntityNotFoundException enfe) {
            System.out.println("Empty");
            request.setAttribute("error", false);
            request.getRequestDispatcher("/accommodation.jsp").forward(request, response);
        } catch (final ParseException | EJBNotFound e) {
            request.setAttribute("error", true);
            request.getRequestDispatcher("/accommodation.jsp").forward(request, response);
        }


    }

    public void destroy() {
    }
}
