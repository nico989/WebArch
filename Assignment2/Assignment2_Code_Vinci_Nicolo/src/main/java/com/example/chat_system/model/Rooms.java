package com.example.chat_system.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class Rooms implements Serializable {
    private HashMap<UUID, Room> rooms;

    public Rooms() {
        rooms = new HashMap<>();
    }

    public Room getRoomById(UUID id) {
        return rooms.get(id);
    }

    public HashMap<UUID, Room> getRooms() {
        return rooms;
    }

    public void addRoom(UUID id, Room room) {
        rooms.put(id, room);
    }

    @Override
    public String toString() {
        return "Rooms{" +
                "number of rooms=" + rooms.size() +
                '}';
    }
}
