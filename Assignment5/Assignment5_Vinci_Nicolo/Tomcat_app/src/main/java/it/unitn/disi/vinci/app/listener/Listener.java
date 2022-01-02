package it.unitn.disi.vinci.app.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Listener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
    }
}
