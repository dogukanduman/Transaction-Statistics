package com.n26.dto;

import com.n26.entity.Statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*
 * Statistics Dto class which holds statistics responses.
 * @author ttdduman
 */
public class StatisticsDto {


    private String sum;

    private  String avg;

    private  String max;

    private  String min;

    private int count;

    /**
     * All params constructor
     * But it scales all BigDecimal values to {@code RoundingMode.HALF_UP}
     * @param sum
     * @param avg
     * @param max
     * @param min
     * @param count
     */
    public StatisticsDto(BigDecimal sum, BigDecimal avg, BigDecimal max, BigDecimal min, BigDecimal count) {
        this.sum = String.valueOf(sum.setScale(2, RoundingMode.HALF_UP));
        this.avg = String.valueOf(avg.setScale(2, RoundingMode.HALF_UP));
        this.max = String.valueOf(max.setScale(2, RoundingMode.HALF_UP));
        this.min = String.valueOf(min.setScale(2, RoundingMode.HALF_UP));
        this.count = count.intValue();
    }
    public StatisticsDto(Statistics stats) {
       this(stats.getSum(),stats.getAvg(),stats.getMax(),stats.getMin(),stats.getCount());
    }

    public StatisticsDto(){

    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getAvg() {
        return avg;
    }

    public void setAvg(String avg) {
        this.avg = avg;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "StatisticsDto{" +
                "sum='" + sum + '\'' +
                ", avg='" + avg + '\'' +
                ", max='" + max + '\'' +
                ", min='" + min + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
