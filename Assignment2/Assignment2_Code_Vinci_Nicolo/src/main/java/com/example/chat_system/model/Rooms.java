package com.example.chat_system.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Rooms implements Serializable {
    private ArrayList<Room> rooms;

    public Rooms() {
        rooms = new ArrayList<>();
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "number of rooms=" + rooms.size() +
                '}';
    }
}
