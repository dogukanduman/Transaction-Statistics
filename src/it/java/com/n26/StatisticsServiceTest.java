package com.n26;


import com.n26.dto.StatisticsDto;
import com.n26.dto.TransactionDto;
import com.n26.entity.Statistics;
import com.n26.entity.Transaction;
import com.n26.respository.TransactionDao;
import com.n26.service.StatisticsService;
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
import java.math.MathContext;
import java.time.Instant;
import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsServiceTest {

    @Autowired
    StatisticsService statisticsService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionDao transactionDao;

    @Test
    public void getLastSixtySecondsTest() throws Exception {

        BigDecimal sum = BigDecimal.valueOf(0);
        BigDecimal max = BigDecimal.valueOf(-1);
        BigDecimal min = BigDecimal.valueOf(Integer.MAX_VALUE);
        BigDecimal count = BigDecimal.valueOf(0);
        BigDecimal one = BigDecimal.valueOf(1.00);


        transactionDao.deleteAll();
        int n = 10;
        for (int i = 0; i < n; i++) {
            TransactionDto tr = generateTransactionDto(i, 1);
            transactionService.save(tr);
            Thread.sleep(1000);

            BigDecimal amount = tr.getAmount();
            if (amount.compareTo(min) < 0) {
                min = amount;
            }
            if (amount.compareTo(max) > 0) {
                max = amount;
            }
            sum = sum.add(amount);
            count = count.add(one);

        }

        BigDecimal avg = sum.divide(count, MathContext.DECIMAL128);
        StatisticsDto firstDto = new StatisticsDto(sum, avg, max, min, count);

        StatisticsDto resultDto = statisticsService.getLastSixtySeconds();

        Assert.assertThat(firstDto.getSum(), Matchers.is(resultDto.getSum()));
        Assert.assertThat(firstDto.getAvg(), Matchers.is(resultDto.getAvg()));
        Assert.assertThat(firstDto.getMax(), Matchers.is(resultDto.getMax()));
        Assert.assertThat(firstDto.getMin(), Matchers.is(resultDto.getMin()));
        Assert.assertThat(firstDto.getCount(), Matchers.is(resultDto.getCount()));
    }

    @Test
    public void getLastSixtySecondsOldItemTest() throws Exception {

        transactionDao.deleteAll();
        int n = 10;
        for (int i = 0; i < n; i++) {
            TransactionDto tr = generateTransactionDto(i, 61);
            transactionDao.save(new Transaction(tr.getAmount(), tr.getTimestamp().getTime()));
        }
        StatisticsDto firstDto = new StatisticsDto(new Statistics());

        StatisticsDto resultDto = statisticsService.getLastSixtySeconds();

        Assert.assertThat(firstDto.getSum(), Matchers.is(resultDto.getSum()));
        Assert.assertThat(firstDto.getAvg(), Matchers.is(resultDto.getAvg()));
        Assert.assertThat(firstDto.getMax(), Matchers.is(resultDto.getMax()));
        Assert.assertThat(firstDto.getMin(), Matchers.is(resultDto.getMin()));
        Assert.assertThat(firstDto.getCount(), Matchers.is(resultDto.getCount()));

    }


    public TransactionDto generateTransactionDto(int value, int minusSeconds) {

        TransactionDto tr = new TransactionDto();
        tr.setAmount(new BigDecimal(value));
        Instant currTimeStamp = Instant.now();
        tr.setTimestamp(Date.from(currTimeStamp.minusSeconds(minusSeconds)));
        return tr;
    }
}
