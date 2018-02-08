package ua.nure.vardanian.SummaryTask4.db.exception;

public class AppException extends Exception {

    private static final long serialVersionUID = 8884180502710895063L;

    public AppException() {
        super();
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }
}