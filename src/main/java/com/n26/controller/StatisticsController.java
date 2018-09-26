package com.n26.controller;

import com.n26.dto.StatisticsDto;
import com.n26.service.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Statistics Rest controller. It gives statistics about the transactions.
 *
 * @author ttdduman
 */

@RestController("StatisticsController")
@RequestMapping("/statistics")
public class StatisticsController {
    private static final Logger logger = LoggerFactory.getLogger(StatisticsController.class);

    @Autowired
    StatisticsService statisticsService;

    /**
     * Get statistics about the transactions
     *
     * @return StatisticsDto
     */

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getStatistics() {

        logger.debug("getStatistics is called");

        StatisticsDto statisticsDto = statisticsService.getLastSixtySeconds();
        return new ResponseEntity<StatisticsDto>(statisticsDto, HttpStatus.OK);

    }
}

