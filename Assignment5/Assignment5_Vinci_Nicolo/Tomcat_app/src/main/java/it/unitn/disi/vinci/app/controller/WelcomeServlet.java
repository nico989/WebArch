package it.unitn.disi.vinci.app.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "welcome", value = "/welcome")
public class WelcomeServlet extends HttpServlet {

    public void init() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/welcome.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
