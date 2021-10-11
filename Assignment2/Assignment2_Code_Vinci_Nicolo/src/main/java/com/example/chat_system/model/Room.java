package com.example.chat_system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Room implements Serializable {
    private String name;
    private final ArrayList<Message> messages;

    public Room() {
        this.messages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setName(String name) { this.name = name; }

    public void addMessage(Message message) {
        messages.add(message);
    }

    @Override
    public String toString() {
        return "Room{" +
                "name='" + name + '\'' +
                ", number of messages=" + messages.size() +
                '}';
    }
}
