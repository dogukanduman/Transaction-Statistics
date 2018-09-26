package com.n26.service;

import com.n26.dto.TransactionDto;
import com.n26.exception.FutureDatedTransactionException;
import com.n26.exception.OutDatedTransactionException;

public interface TransactionService {

    public TransactionDto save(TransactionDto transactionDto) throws OutDatedTransactionException,FutureDatedTransactionException;
    public int deleteAll();

}
