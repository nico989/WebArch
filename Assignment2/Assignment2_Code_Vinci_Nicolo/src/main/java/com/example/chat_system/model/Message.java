package com.example.chat_system.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message implements Serializable{
    private String writtenBy;
    private String text;
    private LocalDateTime date;

    public Message() {
    }

    public String getWrittenBy() {
        return writtenBy;
    }

    public String getText() {
        return text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setWrittenBy(String writtenBy) {
        this.writtenBy = writtenBy;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Message:" + text + " [From:" + writtenBy + ", Date:" + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH-mm-ss")) +"]";
    }
}
