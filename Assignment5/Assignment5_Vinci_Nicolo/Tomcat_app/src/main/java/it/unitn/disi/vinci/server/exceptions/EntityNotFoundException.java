package it.unitn.disi.vinci.server.exceptions;

public class EntityNotFoundException extends Exception{

    public EntityNotFoundException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
