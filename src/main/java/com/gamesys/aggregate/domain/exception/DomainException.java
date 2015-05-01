package com.gamesys.aggregate.domain.exception;

public class DomainException extends RuntimeException {

    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String errorMessage, Exception exception) {
        super(errorMessage, exception);
    }
}
