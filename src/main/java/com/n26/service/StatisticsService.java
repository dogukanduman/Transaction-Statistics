package com.n26.service;


import com.n26.dto.StatisticsDto;

public interface StatisticsService {

    public StatisticsDto getLastSixtySeconds();
}
