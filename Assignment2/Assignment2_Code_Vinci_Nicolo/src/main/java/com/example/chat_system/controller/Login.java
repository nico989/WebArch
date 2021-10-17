package com.example.chat_system.controller;

import com.example.chat_system.model.Rooms;
import com.example.chat_system.model.User;
import com.example.chat_system.utils.State;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;

public final class Login extends HttpServlet {

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

    private State checkCredentials(ServletContext context, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username.equals("admin") && password.equals(getInitParameter("AdminPassword"))) {
            session.setAttribute("authenticated", true);
            initializeBean(context, session, username);
            return State.ADMIN;
        }

        HashMap<String, String> credentials = (HashMap<String, String>) context.getAttribute("credentials");
        String getPassword = credentials.get(username);
        if (Objects.nonNull(getPassword) && getPassword.equals(password)) {
            session.setAttribute("authenticated", true);
            initializeBean(context, session, username);
            return State.PLAIN_USER;
        }

        return State.UNAUTHENTICATED;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        HttpSession session = request.getSession(true);

        switch(checkCredentials(context, request)) {
            case ADMIN:
                session.setAttribute("authenticated", true);
                request.setAttribute("error", false);
                request.getRequestDispatcher("/adminPage.jsp").forward(request, response);
                break;
            case PLAIN_USER:
                session.setAttribute("authenticated", true);
                request.getRequestDispatcher("/userPage.jsp").forward(request, response);
                break;
            case UNAUTHENTICATED:
                session.setAttribute("authenticated", false);
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