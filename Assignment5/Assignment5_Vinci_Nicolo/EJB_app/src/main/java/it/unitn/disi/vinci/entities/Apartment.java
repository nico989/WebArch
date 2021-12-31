package it.unitn.disi.vinci.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "apartment", schema = "webarch")
public class Apartment extends Accommodation implements Serializable {

    private static final long serialVersionUID = 8815481020514640832L;

    @Column(name = "finalCleaning", nullable = false)
    private int finalCleaning;

    @Column(name = "maxPersons", nullable = false)
    private int maxPersons;

    public Apartment() {
        super();
    }

    public int getFinalCleaning() {
        return finalCleaning;
    }

    public void setFinalCleaning(int final_cleaning) {
        this.finalCleaning = final_cleaning;
    }

    public int getMaxPersons() {
        return maxPersons;
    }

    public void setMaxPersons(int max_n_persons) {
        this.maxPersons = max_n_persons;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Apartment apartment = (Apartment) o;

        if (finalCleaning != apartment.finalCleaning) return false;
        return maxPersons == apartment.maxPersons;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + finalCleaning;
        result = 31 * result + maxPersons;
        return result;
    }
}
