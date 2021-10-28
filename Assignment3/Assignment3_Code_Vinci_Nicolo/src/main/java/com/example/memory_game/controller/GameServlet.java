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
import java.util.*;

@WebServlet(name = "game", value = "/game")
public final class GameServlet extends HttpServlet {

    public void init() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String mode = context.getInitParameter("mode");
        List<Integer> grid = Arrays.asList(1,1,2,2,3,3,4,4,5,5,6,6,7,7,8,8);
        if (mode.equals("production")) {
            Collections.shuffle(grid);
        }
        context.setAttribute("grid", grid);
        request.getRequestDispatcher("/playGame.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        ServletContext context = getServletContext();
        HttpSession session = request.getSession(false);
        int score = Integer.parseInt(request.getParameter("score"));
        String username = (String) session.getAttribute("usernameInSession");
        Games gamesBean = (Games) context.getAttribute("gamesBean");
        Game game = new Game();
        game.setUsername(username);
        game.setScore(score);
        gamesBean.getGames().add(game);
    }

    public void destroy() {
    }
}
