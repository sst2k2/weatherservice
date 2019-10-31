package com.dbs.weather.repository;

import com.dbs.weather.entities.ForecastEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface WeatherRepository extends MongoRepository<ForecastEntity, String> {
    ForecastEntity save(ForecastEntity forecastEntity);

    ForecastEntity getForecastEntityById(String id);

    @Override
    boolean existsById(String id);

    @Override
    void deleteById(String id);

    @Override
    List<ForecastEntity> findAll();


}
