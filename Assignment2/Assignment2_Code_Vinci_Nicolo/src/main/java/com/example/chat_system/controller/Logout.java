package com.example.chat_system.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "logout", value = "/logout")
public final class Logout extends HttpServlet {

    public void init() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.invalidate();
        request.getRequestDispatcher("/login").forward(request, response);
    }

    public void destroy() {
    }
}
