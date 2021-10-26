package com.example.memory_game.model;

import java.io.Serializable;
import java.util.ArrayList;

public final class Games implements Serializable {
    private ArrayList<Game> games = new ArrayList<>();

    public Games() {
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public void setGames(ArrayList<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "Games{" +
                "number of games=" + games.size() +
                '}';
    }
}
