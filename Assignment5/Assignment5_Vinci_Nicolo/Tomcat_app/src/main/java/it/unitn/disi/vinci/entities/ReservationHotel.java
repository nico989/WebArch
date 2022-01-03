package it.unitn.disi.vinci.entities;

import java.io.Serializable;
import java.util.Objects;

public class ReservationHotel extends Reservation implements Serializable {

    private static final long serialVersionUID = 7407293966020858563L;

    private Boolean halfBoard;

    public ReservationHotel() {
        super();
    }

    public Boolean getHalfBoard() {
        return halfBoard;
    }

    public void setHalfBoard(Boolean halfBoard) {
        this.halfBoard = halfBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ReservationHotel that = (ReservationHotel) o;

        return Objects.equals(halfBoard, that.halfBoard);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (halfBoard != null ? halfBoard.hashCode() : 0);
        return result;
    }
}