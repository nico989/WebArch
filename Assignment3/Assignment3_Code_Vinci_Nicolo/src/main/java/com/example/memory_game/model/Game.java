package com.example.memory_game.model;

import java.io.Serializable;

public final class Game implements Serializable {
    private String username;
    private int score;

    public Game() {
    }

    public Game(String username, int score) {
        this.username = username;
        this.score = score;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return score + " - " + username;
    }
}
