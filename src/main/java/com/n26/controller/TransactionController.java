package com.n26.controller;

import com.n26.dto.TransactionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.n26.service.TransactionService;

import javax.validation.Valid;

/**
 * Transaction Rest controller.
 *
 * @author ttdduman
 */

@RestController("TransactionController")
@RequestMapping("/transactions")
public class TransactionController {
    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    @Autowired
    TransactionService transactionService;

    /**
     * Saves transaction
     * @param transactionDto
     * @return HttpStatus.CREATED in header
     */
    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<Void> createTransaction(@Valid @RequestBody TransactionDto transactionDto) {

        logger.debug("createTransaction is called with data:{}",transactionDto);
        transactionService.save(transactionDto);
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);

    }

    /**
     * Deletes all transactions.
     * @return  HttpStatus.NO_CONTENT in header
     */

    @RequestMapping( method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteAllTransactions() {

        logger.debug("deleteAllTransactions is called");
        transactionService.deleteAll();
        HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.NO_CONTENT);

    }


}
