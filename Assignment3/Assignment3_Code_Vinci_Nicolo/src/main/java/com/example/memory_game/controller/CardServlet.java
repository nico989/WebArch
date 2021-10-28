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
import java.util.List;

@WebServlet(name = "card", value = "/card")
public final class CardServlet extends HttpServlet {

    public void init() {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        List<Integer> grid = (List<Integer>) getServletContext().getAttribute("grid");
        int cardValue = grid.get(Integer.parseInt(id));
        JSONObject obj = new JSONObject();
        obj.put("cardValue", cardValue);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(obj.toString());
    }

    public void destroy() {
    }
}