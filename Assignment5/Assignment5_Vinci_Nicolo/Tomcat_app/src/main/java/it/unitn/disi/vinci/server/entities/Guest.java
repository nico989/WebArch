package it.unitn.disi.vinci.server.entities;

import java.io.Serializable;
import java.util.Objects;

public class Guest implements Serializable {

    private static final long serialVersionUID = 2233437979204528659L;

    private Long id;

    private String name;

    private String surname;

    public Guest() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Guest guest = (Guest) o;

        if (!Objects.equals(id, guest.id)) return false;
        if (!Objects.equals(name, guest.name)) return false;
        return Objects.equals(surname, guest.surname);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (surname != null ? surname.hashCode() : 0);
        return result;
    }
}
