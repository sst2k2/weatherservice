package com.dbs.weather.service;

import com.dbs.weather.entities.ForecastEntity;
import com.dbs.weather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class CleanupService {

    @Autowired
    private WeatherRepository repository;

    private Logger logger = LoggerFactory.getLogger(CleanupService.class);


    @Scheduled(fixedDelay = 86400)
    public void cleanupOldRecords() {
        try {
            logger.info("Running cleanup process!!");
            List<ForecastEntity> forecastEntities = repository.findAll();
            for (ForecastEntity entity : forecastEntities) {
                if(Math.abs(Duration.between(entity.getTime(),LocalDateTime.now()).toDays())>3){
                    repository.deleteById(entity.getId());
                }
            }
        }catch (Exception ex){
            logger.error("Failed to run cleanup service for on %s", LocalDateTime.now().toString());
        }
    }
}
