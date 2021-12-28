package it.unitn.disi.vinci.services.exceptions;

public class EntityCRUDException extends Exception{

    public EntityCRUDException(final String exceptionMessage) {
        super(exceptionMessage);
    }
}
