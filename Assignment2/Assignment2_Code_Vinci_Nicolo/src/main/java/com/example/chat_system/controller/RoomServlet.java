package com.example.chat_system.controller;

import com.example.chat_system.model.Message;
import com.example.chat_system.model.Room;
import com.example.chat_system.model.Rooms;
import com.example.chat_system.model.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.UUID;

@WebServlet(name = "room", value = "/room")
public class RoomServlet extends HttpServlet {
    public void init() {
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Rooms roomsBean = (Rooms) context.getAttribute("rooms");
        String message = request.getParameter("message");
        UUID id = UUID.fromString(request.getParameter("idRoom"));
        Room r = roomsBean.getRoomById(id);
        User userBean = (User) request.getSession().getAttribute("user");
        Message m = new Message();
        m.setWrittenBy(userBean.getUsername());
        m.setText(message);
        m.setDate(LocalDateTime.now());
        r.addMessage(m);
        request.setAttribute("id", id);
        String title = URLDecoder.decode(r.getName(), "UTF-8");
        request.setAttribute("titleRoom", title);
        request.getRequestDispatcher("/roomPage.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext context = getServletContext();
        Rooms roomsBean = (Rooms) context.getAttribute("rooms");
        UUID id = UUID.fromString(request.getParameter("idRoom"));
        Room r = roomsBean.getRoomById(id);
        request.setAttribute("id", id);
        String title = URLDecoder.decode(r.getName(), "UTF-8");
        request.setAttribute("titleRoom", title);
        request.getRequestDispatcher("/roomPage.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
