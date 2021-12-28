package it.unitn.disi.vinci.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "hotel")
public class Hotel extends Accommodation implements Serializable {

    private static final long serialVersionUID = -2040789886273628048L;

    @Column(name = "extra_half_board", nullable = false)
    private int extra_half_board;

    @Column(name = "stars", nullable = false)
    private int stars;

    @Column(name = "places", nullable = false)
    private int places;

    public Hotel() {
        super();
    }

    public int getExtra_half_board() {
        return extra_half_board;
    }

    public void setExtra_half_board(int extra_half_board) {
        this.extra_half_board = extra_half_board;
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

        if (extra_half_board != hotel.extra_half_board) return false;
        if (stars != hotel.stars) return false;
        return places == hotel.places;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + extra_half_board;
        result = 31 * result + stars;
        result = 31 * result + places;
        return result;
    }
}
