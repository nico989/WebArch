package it.unitn.disi.vinci.entities;

import java.io.Serializable;

public class ReservationHotel extends Reservation implements Serializable {

    private static final long serialVersionUID = 7407293966020858563L;

    private boolean halfBoard;

    public ReservationHotel() {
        super();
    }

    public boolean isHalfBoard() {
        return halfBoard;
    }

    public void setHalfBoard(boolean halfBoard) {
        this.halfBoard = halfBoard;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ReservationHotel that = (ReservationHotel) o;

        return halfBoard == that.halfBoard;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (halfBoard ? 1 : 0);
        return result;
    }
}