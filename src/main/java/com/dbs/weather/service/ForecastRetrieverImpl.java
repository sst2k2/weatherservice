package com.dbs.weather.service;

import com.dbs.weather.entities.ForecastEntity;
import com.dbs.weather.exceptions.ExternalServiceGatewayException;
import com.dbs.weather.exceptions.ExternalServiceInvocationException;
import com.dbs.weather.model.CurrentForecast;
import com.dbs.weather.model.Forecast;
import com.dbs.weather.model.LatLng;
import com.dbs.weather.model.MapAPIResponse;
import com.dbs.weather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ForecastRetrieverImpl implements ForecastRetriever {

	public static final String FORECAST_IO_SERVICE_NAME = "ForecastIOException";
	public static final String MAP_IO_SERVICE_NAME = "MapIOException";
	protected static final String ARG_LATITUDE = "latitude";
	protected static final String ARG_LONGITUDE = "longitude";

	@Autowired
	private MapInfoRetriever mapInfoRetriever;

	@Autowired
	private WeatherRepository repository;

	@Value("${darksky.base.url}")
	private String darkskyBaseUrl;

	@Autowired
	private RestTemplate restTemplate;

	@Value("${locations}")
	private String locations;

	private Logger logger = LoggerFactory.getLogger(ForecastRetrieverImpl.class);

	private Map<String,String> buildURLMap(Double longitude, Double latitude) {
		Map<String,String> arguments = new HashMap<>();
		arguments.put(ARG_LATITUDE, String.valueOf(latitude));
		arguments.put(ARG_LONGITUDE, String.valueOf(longitude));
		return arguments;
	}
	

	private ForecastEntity getForecastFor(Double longitude, Double latitude, String address, LocalDateTime time) {
		ForecastEntity entity = null;
		String searchId = buildId(time, address);
			if (repository.existsById(searchId)) {
				logger.info("Fetch todays record for id : {}", searchId);
				entity = repository.getForecastEntityById(searchId);
				LocalDateTime stored = entity.getTime();
				LocalDateTime current = LocalDateTime.now();
				if (Duration.between(stored, current).toDays() == 0) {
					return entity;
				}
			}

		try{
		logger.info("Call DarkSky API for today for id : {}", searchId);
		Forecast forecast = restTemplate.getForObject(darkskyBaseUrl,Forecast.class, buildURLMap(longitude, latitude));
		entity = processForecast(forecast, address);
		repository.save(entity);
		return entity;
		} catch (HttpStatusCodeException httpStatusEx) {
			logger.error(httpStatusEx.getMessage());
			throw new ExternalServiceInvocationException(FORECAST_IO_SERVICE_NAME, httpStatusEx.getRawStatusCode());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new ExternalServiceGatewayException(FORECAST_IO_SERVICE_NAME, ex);
		}
	}

	@Override
	public List<ForecastEntity> getForecasts(){
		List<ForecastEntity> forecasts = new ArrayList<>();
		String []locationArray = locations.split(",");
		for(String location:locationArray) {
			String city = location.split("/")[0];
			String state = location.split("/")[1];
			MapAPIResponse mapResponse = mapInfoRetriever.getMapInfoFor(city, state);
			LatLng latLng = mapResponse.getResults().get(0).getLocations().get(0).getLatLng();
			String address = buildSearchAddress(city, state);
			LocalDateTime today = LocalDateTime.now();
			forecasts.add(getForecastFor(latLng.getLng(), latLng.getLat(), address, today));
		}
		return forecasts;
	}

	String buildSearchAddress(String city, String state) {
		StringBuilder builder = new StringBuilder();
		builder.append(city);
		builder.append("/");
		builder.append(state);
		return builder.toString();
	}

	private ForecastEntity processForecast(Forecast forecast, String address){
		ForecastEntity entity = new ForecastEntity();
		entity.setLatitude(forecast.getLatitude());
		entity.setLongitude(forecast.getLongitude());
		entity.setDewPoint(forecast.getCurrently().getDewPoint());
		entity.setPrecipProbability(forecast.getCurrently().getPrecipProbability()*100);
		entity.setTemperature(forecast.getCurrently().getTemperature());
		entity.setWindSpeed(forecast.getCurrently().getWindSpeed());
		entity.setSummary(forecast.getCurrently().getSummary());
		entity.setTimezone(forecast.getTimezone());
		entity.setTime(forecast.getCurrently().getTime());
		entity.setAddress(address);
		entity.setId(buildId(forecast.getCurrently().getTime(), address));
		return entity;
	}

	private String buildId(LocalDateTime time, String address) {
		return String.format("%s-%s",address,getFormattedDate(time));
	}

	private String getFormattedDate(LocalDateTime time){
		DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = formatter.format(time);
		return formattedDate;
	}

}
