package org.ruhlendavis.mumue.interpreter;

class UnknownCommandIdentifierException extends RuntimeException {
    public UnknownCommandIdentifierException(String message) {
        super(message);
    }
}
