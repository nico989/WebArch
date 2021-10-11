package com.example.chat_system.controller;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "logout", value = "/logout")
public class Logout extends HttpServlet {

    public void init() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        request.setAttribute("error", false);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
