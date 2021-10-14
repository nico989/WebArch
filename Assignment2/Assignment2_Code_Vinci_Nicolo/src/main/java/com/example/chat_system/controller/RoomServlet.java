package com.example.chat_system.controller;

import com.example.chat_system.model.Message;
import com.example.chat_system.model.Room;
import com.example.chat_system.model.Rooms;
import com.example.chat_system.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@WebServlet(name = "room", value = "/room")
public class RoomServlet extends HttpServlet {
    public void init() {
    }

    private String getDecodedTitle(String title) throws UnsupportedEncodingException {
        return URLDecoder.decode(title, "UTF-8");
    }

    private void addNewMessage(Room room, User user, String message) {
        Message m = new Message();
        m.setWrittenBy(user.getUsername());
        m.setText(message);
        m.setDate(LocalDateTime.now());
        room.addMessage(m);
    }

    private void setSessionAttribute(HttpSession session, UUID id, String title) throws UnsupportedEncodingException {
        if (Objects.isNull(session.getAttribute("idRoomInSession"))) {
            session.setAttribute("idRoomInSession", id);
        }
        if (Objects.isNull(session.getAttribute("titleRoomInSession"))) {
            session.setAttribute("titleRoomInSession", getDecodedTitle(title));
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Rooms roomsBean = (Rooms) getServletContext().getAttribute("rooms");
        HttpSession session = request.getSession();
        User userBean = (User) session.getAttribute("user");

        String message = request.getParameter("message");

        UUID id = (UUID) session.getAttribute("idRoomInSession");
        Room room = roomsBean.getRoomById(id);

        if (!message.isEmpty()) {
            addNewMessage(room, userBean, message);
        }

        request.getRequestDispatcher("/roomPage.jsp").forward(request, response);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Rooms roomsBean = (Rooms) getServletContext().getAttribute("rooms");
        HttpSession session = request.getSession();

        UUID idRoom = (UUID) session.getAttribute("idRoomInSession");
        if (Objects.isNull(idRoom)) {
            idRoom = UUID.fromString(request.getParameter("idRoom"));
        }

        Room room = roomsBean.getRoomById(idRoom);
        String title = room.getName();

        setSessionAttribute(session, idRoom, title);

        request.getRequestDispatcher("/roomPage.jsp").forward(request, response);
    }

    public void destroy() {
    }
}
