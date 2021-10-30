package com.example.memory_game.controller;

import com.example.memory_game.model.Games;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

@WebServlet(name = "login", value = "/login")
public final class LoginServlet extends HttpServlet {

    public void init() {
    }

    private void initializeBean(ServletContext context) {
        if (Objects.isNull(context.getAttribute("gamesBean"))) {
            context.setAttribute("gamesBean", new Games());
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String username = request.getParameter("usernameInRequest");
        if (!username.isEmpty()) {
            initializeBean(context);
            ((ArrayList<String>) context.getAttribute("users")).add(username);
            request.getSession(false).setAttribute("usernameInSession", username);
            response.sendRedirect( request.getContextPath() + "/user");
        } else {
            request.setAttribute("error", true);
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("error", false);
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
