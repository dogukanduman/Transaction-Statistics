package com.n26.service;

import com.n26.entity.Statistics;
import com.n26.dto.StatisticsDto;
import com.n26.respository.TransactionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatisticsServiceImp implements StatisticsService{

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImp.class);

    @Autowired
    TransactionDao transactionDao;


    @Override
    public StatisticsDto getLastSixtySeconds() {

        logger.info("StatisticsServiceImp.getLastSixtySeconds called");

        Statistics stats = transactionDao.getLastSixtySeconds();
        return new StatisticsDto(stats);
    }
}
