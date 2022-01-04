package it.unitn.disi.vinci.services.routine;

import it.unitn.disi.vinci.services.exceptions.EntityCRUDException;
import it.unitn.disi.vinci.services.exceptions.EntityInputException;
import it.unitn.disi.vinci.services.exceptions.EntityNotFoundException;

import java.text.ParseException;

public interface RoutineService {
    public void routineHotel() throws EntityNotFoundException, ParseException, EntityCRUDException, EntityInputException;
    public void routineApartment() throws EntityNotFoundException, ParseException, EntityCRUDException, EntityInputException;
    public void cleanAll() throws EntityNotFoundException, EntityCRUDException;
}
