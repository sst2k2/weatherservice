package com.dbs.weather.service;

import com.dbs.weather.entities.ForecastEntity;
import com.dbs.weather.model.CurrentForecast;
import com.dbs.weather.model.Forecast;

import java.util.List;

public interface ForecastRetriever {

	public List<ForecastEntity> getForecasts();
}
