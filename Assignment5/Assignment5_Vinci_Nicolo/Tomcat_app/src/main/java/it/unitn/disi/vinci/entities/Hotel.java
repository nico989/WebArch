package it.unitn.disi.vinci.entities;

import java.io.Serializable;

public class Hotel extends Accommodation implements Serializable {

    private static final long serialVersionUID = -2040789886273628048L;

    private int extraHalfBoard;

    private int stars;

    private int places;

    public Hotel() {
        super();
    }

    public int getExtraHalfBoard() {
        return extraHalfBoard;
    }

    public void setExtraHalfBoard(int extra_half_board) {
        this.extraHalfBoard = extra_half_board;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Hotel hotel = (Hotel) o;

        if (extraHalfBoard != hotel.extraHalfBoard) return false;
        if (stars != hotel.stars) return false;
        return places == hotel.places;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + extraHalfBoard;
        result = 31 * result + stars;
        result = 31 * result + places;
        return result;
    }
}
