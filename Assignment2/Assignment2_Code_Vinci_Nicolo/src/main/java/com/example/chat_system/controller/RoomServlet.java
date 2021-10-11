package com.example.chat_system.controller;

import com.example.chat_system.model.Room;
import com.example.chat_system.model.Rooms;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@WebServlet(name = "room", value = "/room")
public class RoomServlet extends HttpServlet {
    public void init() {
    }

    private String getEncodedTitle(String title) throws UnsupportedEncodingException {
        return URLEncoder.encode(title, "UTF-8");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = getEncodedTitle(request.getParameter("title"));
        ServletContext context = getServletContext();
        Rooms rooms = (Rooms) context.getAttribute("rooms");
        Room r= new Room();
        r.setName(title);
        rooms.addRoom(r);
        request.getRequestDispatcher("/roomPage.jsp").forward(request, response);

    }

    public void destroy() {
    }
}
