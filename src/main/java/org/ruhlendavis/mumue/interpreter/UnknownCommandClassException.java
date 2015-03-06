package org.ruhlendavis.mumue.interpreter;

class UnknownCommandClassException extends RuntimeException {
    public UnknownCommandClassException(String message) {
        super(message);
    }
}
