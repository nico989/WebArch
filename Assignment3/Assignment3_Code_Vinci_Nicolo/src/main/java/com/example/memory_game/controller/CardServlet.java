package com.example.memory_game.controller;

import org.json.simple.JSONObject;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "card", value = "/card")
public class CardServlet extends HttpServlet {

    public void init() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        String id = request.getParameter("id");
        ArrayList<Integer> grid = (ArrayList<Integer>) context.getAttribute("grid");
        Integer cardValue = grid.get(Integer.parseInt(id));
        JSONObject obj = new JSONObject();
        obj.put("cardValue", cardValue);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(obj.toString());

    }

    public void destroy() {
    }
}