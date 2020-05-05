package dev.coomware.libs.exception;

public class MalformedStateException extends Exception {

    public MalformedStateException() {
        super("An element which should not have an invalid state has been reported as either missing, null, or otherwise empty.\n" +
                "Check any methods which throw or catch this exception for a better understanding of what exactly happened.");
    }

    public MalformedStateException(String ex) {
        super(ex);
    }

    public MalformedStateException(Throwable throwable) {
        super(throwable);
    }
}