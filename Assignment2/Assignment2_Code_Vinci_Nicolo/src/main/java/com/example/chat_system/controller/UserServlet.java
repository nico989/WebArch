package com.example.chat_system.controller;

import com.example.chat_system.model.Room;
import com.example.chat_system.model.Rooms;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.UUID;

@WebServlet(name = "user", value = "/user")
public class UserServlet extends HttpServlet {
    public void init() {
    }

    private String getEncodedTitle(String title) throws UnsupportedEncodingException {
        return URLEncoder.encode(title, "UTF-8");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = getEncodedTitle(request.getParameter("title"));
        ServletContext context = getServletContext();
        Rooms roomsBean = (Rooms) context.getAttribute("rooms");
        Room r= new Room();
        r.setName(title);
        roomsBean.addRoom(UUID.randomUUID(), r);
        request.getRequestDispatcher("/userPage.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/userPage.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
