package com.example.chat_system.listener;

import javax.servlet.*;
import java.io.*;
import java.util.HashMap;
import java.util.Objects;

public class FileListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        HashMap<String, String> credentials = new HashMap<>();
        FileReader fileReader;
        try {
            fileReader = new FileReader(context.getRealPath("/") + context.getInitParameter("PathToAuthentication"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            String[] splitted;
            while (Objects.nonNull(line)) {
                splitted = line.split("-");
                credentials.put(splitted[0], splitted[1]);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        context.setAttribute("credentials", credentials);
    }

    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        HashMap<String, String> credentials = (HashMap<String, String>) context.getAttribute("credentials");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(context.getRealPath("/") + context.getInitParameter("PathToAuthentication"), false);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            for (HashMap.Entry<String , String> entry : credentials.entrySet()) {
                bufferedWriter.write(entry.getKey() + "-" + entry.getValue() + "\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
