package com.example.memory_game.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Collectors;

public final class Games implements Serializable {
    private final ArrayList<Game> games = new ArrayList<>();

    public Games() {
    }

    public ArrayList<Game> getGames() {
        return games;
    }

    public ArrayList<Game> getOrderedGames() {
        games.sort(Comparator.comparingInt(Game::getScore).reversed());
        if (games.size() > 5) {
            return (ArrayList<Game>) games.stream().limit(5).collect(Collectors.toList());
        } else {
            return games;
        }
    }

    public void setGames(ArrayList<Game> games) {
        Collections.copy(this.games, games);
    }

    @Override
    public String toString() {
        return "Games{" +
                "number of games=" + games.size() +
                '}';
    }
}
