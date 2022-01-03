package it.unitn.disi.vinci.app.model;

import java.io.Serializable;
import java.util.Date;

public class UserRequest implements Serializable {

    public enum Type {
        Hotel,
        Apartment
    }

    private String name;
    private String surname;
    private int accommodationId;
    private Date dateFrom;
    private Date dateTo;
    private int nPersons;
    private boolean extraHalfBoard;
    private Type type;

    public UserRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(int accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }

    public int getnPersons() {
        return nPersons;
    }

    public void setnPersons(int nPersons) {
        this.nPersons = nPersons;
    }

    public boolean isExtraHalfBoard() {
        return extraHalfBoard;
    }

    public void setExtraHalfBoard(boolean extraHalfBoard) {
        this.extraHalfBoard = extraHalfBoard;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserRequest{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", accommodationId=" + accommodationId +
                ", dateFrom=" + dateFrom +
                ", dateTo=" + dateTo +
                ", nPersons=" + nPersons +
                ", extraHalfBoard=" + extraHalfBoard +
                ", type=" + type +
                '}';
    }
}
