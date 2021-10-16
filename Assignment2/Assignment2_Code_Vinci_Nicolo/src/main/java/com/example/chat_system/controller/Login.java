package com.example.chat_system.controller;

import com.example.chat_system.model.Rooms;
import com.example.chat_system.model.User;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public class Login extends HttpServlet {

    public void init() {
    }

    private void initializeBean(ServletContext context, HttpSession session, String username) {
        if (Objects.isNull(session.getAttribute("user"))) {
            session.setAttribute("user", new User());
        }
        User user = (User) session.getAttribute("user");
        user.setUsername(username);

        if (Objects.isNull(context.getAttribute("rooms"))) {
            context.setAttribute("rooms", new Rooms());
        }

    }

    private int checkCredentials(ServletContext context, HttpServletRequest request) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.equals("admin") && password.equals(getInitParameter("AdminPassword"))) {
            initializeBean(context, request.getSession(), username);
            return 2;
        }

        HashMap<String, String> credentials = (HashMap<String, String>) context.getAttribute("credentials");
        if (Objects.nonNull(credentials.get(username))) {
            initializeBean(context, request.getSession(), username);
            return 1;
        }

        return 3;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();

        switch(checkCredentials(context, request)) {
            case 1:
                request.getRequestDispatcher("/userPage.jsp").forward(request, response);
                break;
            case 2:
                request.getRequestDispatcher("/adminPage.jsp").forward(request, response);
                break;
            case 3:
                request.setAttribute("error", true);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", false);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    public void destroy() {
    }
}