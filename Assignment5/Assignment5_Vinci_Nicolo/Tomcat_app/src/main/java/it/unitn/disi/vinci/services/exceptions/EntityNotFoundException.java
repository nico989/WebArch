package it.unitn.disi.vinci.services.exceptions;

public class EntityNotFoundException extends Exception{

    public EntityNotFoundException(final String exceptionMessage) {
        super(exceptionMessage);
    }
}
