package com.n26.exception;

/*
 * OutDatedTransactionException
 * When throws transaction timestamp is older then a period of time.
 * @author ttdduman
 */
public class OutDatedTransactionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public OutDatedTransactionException() {
    }

    public OutDatedTransactionException(String message) {
        super(message);
    }

    public OutDatedTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

    public static OutDatedTransactionException build(int seconds) {
            return new OutDatedTransactionException("Transaction timestamp older than "+ seconds +" seconds.");
    }
}
