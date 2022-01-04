package it.unitn.disi.vinci.app.listener;

import it.unitn.disi.vinci.app.locator.ServiceLocator;
import it.unitn.disi.vinci.app.locator.exceptions.EJBNotFound;
import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;
import it.unitn.disi.vinci.services.routine.RoutineService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.text.ParseException;

public class Listener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {

        try {
            ServiceLocator.getInstance().ejbLookUp(RoutineService.class).routineHotel();
            ServiceLocator.getInstance().ejbLookUp(RoutineService.class).routineApartment();
        } catch (final EntityNotFoundException | ParseException | EntityCRUDException | EntityInputException | EJBNotFound e) {
            System.exit(1);
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {

        try {
            ServiceLocator.getInstance().ejbLookUp(RoutineService.class).cleanAll();
        } catch (final EntityNotFoundException | EntityCRUDException | EJBNotFound e) {
            System.exit(1);
        }
    }
}
