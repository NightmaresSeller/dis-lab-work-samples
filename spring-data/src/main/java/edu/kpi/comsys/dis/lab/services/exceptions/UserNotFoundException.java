package edu.kpi.comsys.dis.lab.services.exceptions;

public class UserNotFoundException extends EntityNotFoundException {

    private final Long userId;

    public UserNotFoundException(Long userId) {
        super("User with id \"" + userId + "\" not found");
        this.userId = userId;
    }

    public UserNotFoundException(String message, Long userId) {
        super(message);
        this.userId = userId;
    }

    public UserNotFoundException(String message, Throwable cause, Long userId) {
        super(message, cause);
        this.userId = userId;
    }

    public UserNotFoundException(Throwable cause, Long userId) {
        super("User with id \"" + userId + "\" not found", cause);
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

}
