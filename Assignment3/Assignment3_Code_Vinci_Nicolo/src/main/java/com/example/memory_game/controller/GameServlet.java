package com.example.memory_game.controller;

import com.example.memory_game.model.Game;
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
import java.util.List;
import java.util.Random;

@WebServlet(name = "game", value = "/game")
public final class GameServlet extends HttpServlet {

    public void init() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String mode = context.getInitParameter("mode");
        System.out.println(mode);
        ArrayList<Integer> grid = new ArrayList<>();
        Random rand = new Random();
        for (int i=0; i < 16; i++) {
            grid.add(rand.nextInt(9));
        }
        System.out.println(grid);
        context.setAttribute("grid", grid);
        request.getRequestDispatcher("/playGame.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        HttpSession session = request.getSession(false);
        Integer score = Integer.valueOf(request.getParameter("score"));
        String username = (String) session.getAttribute("usernameInSession");
        System.out.println(score);
        Games gamesBean = (Games) context.getAttribute("gamesBean");
        Game game = new Game();
        game.setUsername(username);
        game.setScore(score);
        gamesBean.getGames().add(game);
    }

    public void destroy() {
    }
}
