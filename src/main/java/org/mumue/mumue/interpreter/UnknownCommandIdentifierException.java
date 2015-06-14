package org.mumue.mumue.interpreter;

class UnknownCommandIdentifierException extends RuntimeException {
    public UnknownCommandIdentifierException(String message) {
        super(message);
    }
}
