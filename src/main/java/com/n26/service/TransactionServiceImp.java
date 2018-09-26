package com.n26.service;

import com.n26.entity.Transaction;
import com.n26.dto.TransactionDto;
import com.n26.exception.FutureDatedTransactionException;
import com.n26.exception.OutDatedTransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.n26.respository.TransactionDao;

import java.time.Instant;
import java.util.Date;

@Service
public class TransactionServiceImp  implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImp.class);
    private static final int TIME_CONSTRAINT_IN_SECONDS = 60;

    @Autowired
    TransactionDao transactionDao;

    public TransactionDto save(TransactionDto transactionDto) throws OutDatedTransactionException,FutureDatedTransactionException {
        logger.info("TransactionServiceImp.save is called");
        Date trDate = transactionDto.getTimestamp();
        Instant instantTrDate = trDate.toInstant();

        Instant currTimeStamp = Instant.now();

        if( instantTrDate.plusSeconds(TIME_CONSTRAINT_IN_SECONDS).isBefore(currTimeStamp)){
           throw OutDatedTransactionException.build(TIME_CONSTRAINT_IN_SECONDS);
        }

        if( instantTrDate.isAfter(currTimeStamp)){
            throw FutureDatedTransactionException.build();
        }

        long milliseconds = transactionDto.getTimestamp().getTime();
        Transaction tr= new Transaction(transactionDto.getAmount(),milliseconds);
        transactionDao.save(tr);

        return transactionDto;

    }
    public int  deleteAll(){
        logger.info("TransactionServiceImp.deleteAll is called");return transactionDao.deleteAll();
    }
}
