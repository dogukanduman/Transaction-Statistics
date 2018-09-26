package com.n26.respository;


import com.n26.entity.Statistics;
import com.n26.entity.Transaction;

public interface TransactionDao {

    /**
     * Saves transaction
     * @param transaction
     * @return
     */
    public Transaction save(Transaction transaction);

    /**
     * Deletes all transactions
     * @return
     */
    public int deleteAll();

    /**
     * Get last Sixty Seconds transaction statistics
     * @return
     */
    public Statistics getLastSixtySeconds();

    /**
     * Gets transaction from cache
     * @param milliseconds as key
     * @return
     */
    public Transaction get(Long milliseconds);

    /**
     * Gets number of items in cache
     * @return
     */
    public int getSize();

}
