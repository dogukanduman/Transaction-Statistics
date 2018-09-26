package com.n26;


import com.n26.dto.TransactionDto;
import com.n26.entity.Transaction;
import com.n26.exception.FutureDatedTransactionException;
import com.n26.exception.OutDatedTransactionException;
import com.n26.respository.TransactionDao;
import com.n26.service.TransactionService;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionServiceTest {


    @Autowired
    TransactionDao transactionDao;

    @Autowired
    TransactionService transactionService;


    @Test
    public void save() throws Exception {
        TransactionDto tr = new TransactionDto();
        tr.setAmount(new BigDecimal(1.00));

        Instant currTimeStamp = Instant.now();
        tr.setTimestamp(Date.from(currTimeStamp));
        transactionService.save(tr);

        Transaction savedTr = transactionDao.get(currTimeStamp.toEpochMilli());

        Assert.assertThat(tr.getAmount(), Matchers.is(savedTr.getAmount()));
        Assert.assertThat(tr.getTimestamp().getTime(), Matchers.is(savedTr.getMilliseconds()));

    }

    @Test(expected = OutDatedTransactionException.class)
    public void OutDatedTransactionExceptionTest() {

        TransactionDto tr = new TransactionDto();
        tr.setAmount(new BigDecimal(1.00));
        Instant currTimeStamp = Instant.now();
        tr.setTimestamp(Date.from(currTimeStamp.minusSeconds(120)));
        transactionService.save(tr);
    }

    @Test(expected = FutureDatedTransactionException.class)
    public void FutureDatedTransactionExceptionTest() {
        TransactionDto tr = new TransactionDto();
        tr.setAmount(new BigDecimal(1.00));
        Instant currTimeStamp = Instant.now();
        tr.setTimestamp(Date.from(currTimeStamp.plusSeconds(120)));
        transactionService.save(tr);
    }


    @Test
    public void deleteAll() throws Exception {

        transactionDao.deleteAll();
        int n = 2;

        for (int i = 0; i < n; i++) {
            transactionService.save(generateTransactionDto());
        }

        Assert.assertThat(transactionDao.getSize(), Matchers.is(n));

        transactionService.deleteAll();

        Assert.assertThat(transactionDao.getSize(), Matchers.is(0));


        n = 5;

        for (int i = 0; i < n; i++) {
            transactionService.save(generateTransactionDto());
        }

        Assert.assertThat(transactionDao.getSize(), Matchers.is(n));

        transactionService.deleteAll();

        Assert.assertThat(transactionDao.getSize(), Matchers.is(0));

    }

    private TransactionDto generateTransactionDto() {

        TransactionDto tr = new TransactionDto();
        tr.setAmount(new BigDecimal(1.00));
        Instant currTimeStamp = Instant.now();
        tr.setTimestamp(Date.from(currTimeStamp.minusSeconds(1)));
        return tr;
    }
}
