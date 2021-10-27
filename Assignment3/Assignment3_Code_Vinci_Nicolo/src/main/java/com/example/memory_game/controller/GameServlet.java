package com.example.memory_game.controller;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@WebServlet(name = "game", value = "/game")
public class GameServlet extends HttpServlet {

    public void init() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        ArrayList<Integer> grid = new ArrayList<>();
        Random rand = new Random();
        for (int i=0; i < 16; i++) {
            grid.add(rand.nextInt(9));
        }
        System.out.println(grid);
        context.setAttribute("grid", grid);
        request.getRequestDispatcher("/playGame.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
