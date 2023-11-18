package pl.milosz.booksmanagement.exception;

public class HttpConnectionException extends RuntimeException{
    public HttpConnectionException(String message) {
        super(message);
    }
}
