package com.example.memory_game.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "login", value = "/login")
public final class LoginServlet extends HttpServlet {

    public void init() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        HttpSession session = request.getSession(false);
        String username = request.getParameter("usernameInRequest");
        if (!username.isEmpty()) {
            ArrayList<String> users = (ArrayList<String>) context.getAttribute("users");
            users.add(username);
            session.setAttribute("usernameInSession", username);
            request.getRequestDispatcher("/userPage.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    public void destroy() {
    }
}
