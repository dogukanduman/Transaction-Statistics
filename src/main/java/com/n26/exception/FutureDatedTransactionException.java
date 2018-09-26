package com.n26.exception;

/*
 * FutureDatedTransactionException
 * When throws transaction timestamp is in the future.
 * @author ttdduman
 */
public class FutureDatedTransactionException extends RuntimeException{


    private static final long serialVersionUID = 1L;

    public FutureDatedTransactionException() {
    }

    public FutureDatedTransactionException(String message) {
        super(message);
    }

    public FutureDatedTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FutureDatedTransactionException build() {
        return new FutureDatedTransactionException("Timestamp must be equal or older than current time");
    }


}
