package com.example.chat_system.controller;

import java.io.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "login", value = "/login")
public class Login extends HttpServlet {

    public void init() { }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");

        String user = request.getParameter("username");
        String pass = request.getParameter("password");

        //out.println("<h1>" + user +"</h1>");
        //out.println("<h1>" + pass +"</h1>");
        if (user.equals("ciao")) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }

    public void destroy() { }
}