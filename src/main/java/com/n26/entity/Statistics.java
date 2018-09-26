package com.n26.entity;

import java.math.BigDecimal;


/*
 * Entity class which holds Statistics.
 * @author ttdduman
 */
public class Statistics {

    private BigDecimal sum;

    private BigDecimal avg;

    private BigDecimal max;

    private BigDecimal min;

    private BigDecimal count;

    /**
     * All params constructor
     * @param sum
     * @param avg
     * @param max
     * @param min
     * @param count
     */
    public Statistics(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, BigDecimal count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    /**
     * None param constructor
     * Sets all paramters to 0.00
     */
    public Statistics() {
        this.sum = BigDecimal.valueOf(0.00);
        this.avg = BigDecimal.valueOf(0.00);
        this.max = BigDecimal.valueOf(0.00);
        this.min = BigDecimal.valueOf(0.00);
        this.count = BigDecimal.valueOf(0.00);
    }


    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public BigDecimal getAvg() {
        return avg;
    }

    public void setAvg(BigDecimal avg) {
        this.avg = avg;
    }

    public BigDecimal getMax() {
        return max;
    }

    public void setMax(BigDecimal max) {
        this.max = max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public void setMin(BigDecimal min) {
        this.min = min;
    }

    public BigDecimal getCount() {
        return count;
    }

    public void setCount(BigDecimal count) {
        this.count = count;
    }
}
