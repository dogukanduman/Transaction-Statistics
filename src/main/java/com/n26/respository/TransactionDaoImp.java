package com.n26.respository;


import com.n26.entity.Statistics;
import com.n26.entity.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TransactionDaoImp implements TransactionDao {

    private static final Logger logger = LoggerFactory.getLogger(TransactionDaoImp.class);

    private AtomicLong numberOfTransaction = new AtomicLong(0L);

    /**
     * ConcurrentSkipListMap obtains us concurrent structer which is similar to treemap
     * Use timestamp as a key
     * Use List<Transaction> as a value.
     * It keeps List because some transactions may have same timestamp
     * First check is there any transactions with same key
     * If it is, add it to list not create list and add it.
     */
    private ConcurrentSkipListMap<Long, List<Transaction>> transactionListMap = new ConcurrentSkipListMap<Long, List<Transaction>>();

    public Transaction save(Transaction transaction) {

        /**Key */
        Long key = transaction.getMilliseconds();

        List<Transaction> transactionList = transactionListMap.get(key);

        if (transactionList == null) {
            transactionList = new ArrayList<>();
        }
        transactionList.add(transaction);

        transactionListMap.put(key, transactionList);

        numberOfTransaction.getAndIncrement();

        return transaction;
    }

    public int deleteAll() {
        int size = numberOfTransaction.intValue();
        transactionListMap = null;
        transactionListMap = new ConcurrentSkipListMap<Long, List<Transaction>>();
        numberOfTransaction = new AtomicLong(0L);
        return size;
    }

    public Statistics getLastSixtySeconds() {


        /** Take a snaphot for last 60 seconds*/
        Instant currTimeStamp = Instant.now();
        Long fromKey = currTimeStamp.minusSeconds(60).toEpochMilli();
        ConcurrentNavigableMap<Long, List<Transaction>> transactionsInLastSixtySeconds = transactionListMap.tailMap(fromKey);

        BigDecimal sum = BigDecimal.valueOf(0);
        BigDecimal max = BigDecimal.valueOf(-1);
        BigDecimal min = BigDecimal.valueOf(Integer.MAX_VALUE);
        BigDecimal count = BigDecimal.valueOf(0);
        BigDecimal one = BigDecimal.valueOf(1.00);
        Set<Map.Entry<Long, List<Transaction>>> entrySet = transactionsInLastSixtySeconds.entrySet();

        /**There is no transaction in las 60 seconds */
        if(entrySet.isEmpty()){
            return new Statistics();
        }

        /** Iterate snapshot and calculate statistics*/
        for (Map.Entry<Long, List<Transaction>> entry : entrySet) {
            List<Transaction> transactionList = entry.getValue();
            for (Transaction tr : transactionList) {

                BigDecimal amount = tr.getAmount();
                if (amount.compareTo(min) < 0) {
                    min = amount;
                }
                if (amount.compareTo(max) > 0) {
                    max = amount;
                }
                sum=  sum.add(amount);
                count=  count.add(one);
            }
        }
        logger.info("sum:{}",sum);
        logger.info("max:{}",max);
        logger.info("min:{}",min);
        logger.info("count:{}",count);
        logger.info("avg:{}",sum.divide(count, MathContext.DECIMAL128));


        return new Statistics(sum, sum.divide(count, MathContext.DECIMAL128), max, min, count);
    }

    @Override
    public Transaction get(Long milliseconds) {
        List<Transaction> transactionList = transactionListMap.get(milliseconds);
        if((transactionList==null)||(transactionList.isEmpty())){
            return new Transaction();
        }
        return transactionList.get(0);
    }
    @Override
    public int getSize(){
       return numberOfTransaction.intValue();
    }


}
