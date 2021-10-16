package com.example.chat_system.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Scanner;

public class FileListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        HashMap<String, String> credentials = new HashMap<>();
        InputStream myFile = context.getResourceAsStream(context.getInitParameter("pathToAuthentication" ));
        Scanner myReader = new Scanner(myFile);
        String[] splitted;
        while (myReader.hasNextLine()) {
            splitted = myReader.nextLine().split("-");
            credentials.put(splitted[0], splitted[1]);
        }
        myReader.close();
        context.setAttribute("credentials", credentials);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}
