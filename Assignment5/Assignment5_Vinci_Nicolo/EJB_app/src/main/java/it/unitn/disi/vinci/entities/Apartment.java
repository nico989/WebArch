package it.unitn.disi.vinci.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "apartment")
public class Apartment extends Accommodation implements Serializable {

    private static final long serialVersionUID = 8815481020514640832L;

    @Column(name = "final_cleaning", nullable = false)
    private int final_cleaning;

    @Column(name = "max_n_persons", nullable = false)
    private int max_n_persons;

    public Apartment() {
        super();
    }

    public int getFinal_cleaning() {
        return final_cleaning;
    }

    public void setFinal_cleaning(int final_cleaning) {
        this.final_cleaning = final_cleaning;
    }

    public int getMax_n_persons() {
        return max_n_persons;
    }

    public void setMax_n_persons(int max_n_persons) {
        this.max_n_persons = max_n_persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Apartment apartment = (Apartment) o;

        if (final_cleaning != apartment.final_cleaning) return false;
        return max_n_persons == apartment.max_n_persons;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + final_cleaning;
        result = 31 * result + max_n_persons;
        return result;
    }
}
