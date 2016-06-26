package edu.kpi.comsys.dis.lab.services.exceptions;

public class EntityValidationException extends Exception {

    public EntityValidationException() {
    }

    public EntityValidationException(String message) {
        super(message);
    }

    public EntityValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public EntityValidationException(Throwable cause) {
        super(cause);
    }

}
