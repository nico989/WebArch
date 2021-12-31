package it.unitn.disi.vinci;
import it.unitn.disi.vinci.services.guest.GuestService;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;

public class ServiceLocator {
    private static ServiceLocator serviceLocator=null;
    private Context ctx=null;
    private HashMap<String,Object> map;

    public static synchronized ServiceLocator getInstance(){
        if (serviceLocator == null) {
            serviceLocator = new ServiceLocator(); }
        return serviceLocator;
    }
    private ServiceLocator(){
        getContext();
        map=new HashMap<String,Object>();
    }

    private Context getContext() {
        if (ctx==null) {
            Hashtable jndiProperties= new Hashtable();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
            jndiProperties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
            //jndiProperties.put("remote.connection.default.connect.options.org.xnio.Options.SASL_POLICY_NOANONYMOUS",false);
            /*Properties jndiProperties = new Properties();
            jndiProperties.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jboss.naming.remote.client.InitialContextFactory");
            jndiProperties.put(Context.URL_PKG_PREFIXES,
                    "org.jboss.ejb.client.naming");
            jndiProperties.put(Context.PROVIDER_URL,
                    "http-remoting://localhost:8080");
            jndiProperties.put("jboss.naming.client.ejb.context", true);*/
            try {
                ctx = new InitialContext(jndiProperties);
            } catch (NamingException ex) {
                ex.printStackTrace();            }
        }
        return ctx;
    }

    Object getHandle(String s) {
        Object retval=null;
        if (! map.containsKey(s)) {
            try {
                retval=ctx.lookup(s);
                map.put(s,retval);
            } catch (NamingException e) {
                e.printStackTrace();
            }
        } else {
            retval=map.get(s);
        }
        return retval;
    }

    public <T> T lookup(final Class<T> service) throws Exception {
        final String serviceName = service.getEnclosingClass() != null ? service.getEnclosingClass().getName() : service.getName();

        final String serviceToLookUp;
        final T serviceInstance;

        if (GuestService.class.isAssignableFrom(service)) {
            serviceToLookUp = "ejb:/EJB_app/GuestServiceBean!it.unitn.disi.vinci.services.guest.GuestService";
        } else {
            throw new Exception("error");
        }

        serviceInstance = service.cast(ctx.lookup(serviceToLookUp));

        return serviceInstance;
    }
}
