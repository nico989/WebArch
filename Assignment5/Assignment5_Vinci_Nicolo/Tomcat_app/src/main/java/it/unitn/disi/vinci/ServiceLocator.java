package it.unitn.disi.vinci;
import org.wildfly.naming.client.WildFlyInitialContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Hashtable;

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
}
