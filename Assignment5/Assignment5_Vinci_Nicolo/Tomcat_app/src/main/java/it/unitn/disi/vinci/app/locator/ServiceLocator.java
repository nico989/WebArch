package it.unitn.disi.vinci.app.locator;

import it.unitn.disi.vinci.app.locator.exceptions.EJBNotFound;
import it.unitn.disi.vinci.entities.Reservation;
import it.unitn.disi.vinci.services.apartment.ApartmentService;
import it.unitn.disi.vinci.services.guest.GuestService;
import it.unitn.disi.vinci.services.hotel.HotelService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class ServiceLocator {
    private static ServiceLocator serviceLocator = null;
    private Context context;
    private HashMap<String, Object> cache;

    public static synchronized ServiceLocator getInstance() {
        if (Objects.isNull(serviceLocator)) {
            serviceLocator = new ServiceLocator();
        }
        return serviceLocator;
    }

    private ServiceLocator() {
        try {
            this.context = getContext();
            cache = new HashMap<>();
        } catch (final NamingException e) {
            System.out.printf("Can't initialize context due to %s", e.getMessage());
            System.exit(1);
        }
    }

    private Context getContext() throws NamingException {
        final Properties properties = new Properties();
        properties.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
        properties.setProperty(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        return new InitialContext(properties);
    }

    public <T> T ejbLookUp(final Class<T> requestedEjb) throws EJBNotFound {

        final String ejbToLookUp;
        final T ejb;

        if (cache.containsKey(requestedEjb.getName())) {
            System.out.println("cache");
            ejb = requestedEjb.cast(cache.get(requestedEjb.getName()));
        } else {
            System.out.println("no cache");
            if (ApartmentService.class.isAssignableFrom(requestedEjb)) {
                ejbToLookUp = "ejb:/EJB_app/ApartmentServiceBean!it.unitn.disi.vinci.services.apartment.ApartmentService";
            } else if (GuestService.class.isAssignableFrom(requestedEjb)) {
                ejbToLookUp = "ejb:/EJB_app/GuestServiceBean!it.unitn.disi.vinci.services.guest.GuestService";
            } else if (HotelService.class.isAssignableFrom(requestedEjb)) {
                ejbToLookUp = "ejb:/EJB_app/HotelServiceBean!it.unitn.disi.vinci.services.hotel.HotelService";
            } else if (Reservation.class.isAssignableFrom(requestedEjb)) {
                ejbToLookUp = "ejb:/EJB_app/ReservationServiceBean!it.unitn.disi.vinci.services.reservation.ReservationService";
            } else {
                throw new EJBNotFound(String.format("EJB Interface %s not supported", requestedEjb));
            }

            try {
                ejb = requestedEjb.cast(context.lookup(ejbToLookUp));
                cache.put(requestedEjb.getName(), ejb);
            } catch (final NamingException e) {
                throw new EJBNotFound(String.format("EJB %s not found due to %s", requestedEjb, e.getMessage()));
            }
        }
        return ejb;
    }
}
