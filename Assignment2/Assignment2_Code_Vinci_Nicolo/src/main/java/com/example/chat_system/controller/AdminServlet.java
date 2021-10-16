package com.example.chat_system.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;

@WebServlet(name = "admin", value = "/admin")
public class AdminServlet extends HttpServlet {

    public void init() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        HashMap<String, String> credentials = (HashMap<String, String>) context.getAttribute("credentials");
        String newUsername = request.getParameter("newUsername");
        String newPassword = request.getParameter("newPassword");
        if (!newUsername.isEmpty() && !newPassword.isEmpty()) {
            credentials.put(newUsername, newPassword);
        } else {
            request.setAttribute("error", true);
        }
        request.getRequestDispatcher("/adminPage.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/adminPage.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
