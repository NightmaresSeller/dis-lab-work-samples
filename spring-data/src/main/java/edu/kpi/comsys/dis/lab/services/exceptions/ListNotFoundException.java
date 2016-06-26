package edu.kpi.comsys.dis.lab.services.exceptions;

public class ListNotFoundException extends EntityNotFoundException {

    private final Long listId;

    public ListNotFoundException(Long listId) {
        super("List with id \"" + listId + "\" not found");
        this.listId = listId;
    }

    public ListNotFoundException(String message, Long listId) {
        super(message);
        this.listId = listId;
    }

    public ListNotFoundException(String message, Throwable cause, Long listId) {
        super(message, cause);
        this.listId = listId;
    }

    public ListNotFoundException(Throwable cause, Long listId) {
        super("List with id \"" + listId + "\" not found", cause);
        this.listId = listId;
    }

    public Long getListId() {
        return listId;
    }

}
