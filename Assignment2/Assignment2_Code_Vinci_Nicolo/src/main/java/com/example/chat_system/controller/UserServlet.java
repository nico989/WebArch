package com.example.chat_system.controller;

import com.example.chat_system.model.Room;
import com.example.chat_system.model.Rooms;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

@WebServlet(name = "user", value = "/user")
public final class UserServlet extends HttpServlet {
    public void init() {
    }

    private String getEncodedTitle(String title) throws UnsupportedEncodingException {
        return URLEncoder.encode(title, "UTF-8");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Rooms roomsBean = (Rooms) getServletContext().getAttribute("rooms");
        String title = request.getParameter("title");

        if (!title.isEmpty()) {
            Room r= new Room();
            r.setName(getEncodedTitle(title));
            roomsBean.addRoom(UUID.randomUUID(), r);
        }

        request.getRequestDispatcher("/userPage.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
