package com.example.chat_system.controller;

import com.example.chat_system.model.Message;
import com.example.chat_system.model.Room;
import com.example.chat_system.model.Rooms;
import com.example.chat_system.model.User;
import java.io.*;
import java.util.Objects;
import java.util.Scanner;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {
    private static final String auth = "C:\\Users\\vinci\\Documents\\nicolo\\MaterialeUNI\\Magistrale\\2anno\\1semestre\\WebArchitectures\\assignment\\Assignment2\\Assignment2_Code_Vinci_Nicolo\\src\\docs\\authentication.txt";

    public void init() {
    }

    private boolean checkCredentials(String username, String password) throws IOException {
        boolean authorized = false;
        File myFile = new File(auth);
        Scanner myReader = new Scanner(myFile);
        String data;
        String[] splitted;
        while (myReader.hasNextLine()) {
            data = myReader.nextLine();
            splitted = data.split("-");
            if (splitted[0].equals(username) && splitted[1].equals(password)) {
                authorized = true;
                break;
            }

        }
        myReader.close();
        return authorized;
    }

    private synchronized void initializeBean(ServletContext context, HttpSession session, String username, String password) {
        if (Objects.isNull(session.getAttribute("user"))) {
            session.setAttribute("user", new User());
        }
        User user = (User) session.getAttribute("user");
        user.setUsername(username);
        user.setPassword(password);

        if (Objects.isNull(context.getAttribute("rooms"))) {
            context.setAttribute("rooms", new Rooms());
        }

        if (Objects.isNull(context.getAttribute("room"))) {
            context.setAttribute("room", new Room());
        }

        if (Objects.isNull(context.getAttribute("message"))) {
            context.setAttribute("message", new Message());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        ServletContext context = getServletContext();
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (!username.isEmpty() && !password.isEmpty() && checkCredentials(username, password)) {
            initializeBean(context, session, username, password);
            request.getRequestDispatcher("/roomPage.jsp").forward(request, response);
        } else {
            request.setAttribute("error", true);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    public void destroy() {
    }
}