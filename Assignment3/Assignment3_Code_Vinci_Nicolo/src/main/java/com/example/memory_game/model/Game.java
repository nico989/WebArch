package com.example.memory_game.model;

import java.io.Serializable;

public final class Game implements Serializable, Comparable<Game> {
    private String username;
    private int score;

    public Game() {
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
    public int compareTo(Game o) {
        return Integer.compare(score, o.getScore());
    }

    @Override
    public String toString() {
        return "Game{" +
                "username='" + username + '\'' +
                ", score=" + score +
                '}';
    }
}
