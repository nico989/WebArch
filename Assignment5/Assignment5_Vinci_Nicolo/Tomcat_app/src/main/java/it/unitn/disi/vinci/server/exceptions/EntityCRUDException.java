package it.unitn.disi.vinci.server.exceptions;

public class EntityCRUDException extends Exception{

    public EntityCRUDException(final String exceptionMessage) {
        super(exceptionMessage);
    }
}
