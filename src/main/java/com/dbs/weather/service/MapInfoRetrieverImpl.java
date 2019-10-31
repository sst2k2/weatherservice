package com.dbs.weather.service;

import com.dbs.weather.exceptions.ExternalServiceGatewayException;
import com.dbs.weather.exceptions.ExternalServiceInvocationException;
import com.dbs.weather.model.MapAPIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class MapInfoRetrieverImpl implements MapInfoRetriever {

	protected static final String ARG_CITY = "city";
	protected static final String ARG_STATE = "state";
	protected static final String MAP_GEOCODE_SERVICE ="mapQuestApi";

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment environment;


	private String mapApiKey;

	@Value("${map.base.url}")
	private String mapBaseUrl;

	private Logger logger = LoggerFactory.getLogger(MapInfoRetrieverImpl.class);

	private Map<String,String> buildURLMap(String city, String state) {
		Map<String, String> arguments = new HashMap<String,String>();
		arguments.put(ARG_CITY, city);
		arguments.put(ARG_STATE, state);
		return arguments;
	}
	

	@Override
	public MapAPIResponse getMapInfoFor(String city, String state) {
		try {
			logger.info("GET: Geolocations for {}/{}",city,state);
			MapAPIResponse rawResponse = restTemplate.getForObject(mapBaseUrl,
					MapAPIResponse.class, buildURLMap(city, state));
			return rawResponse;
		} catch (HttpStatusCodeException httpStatusEx) {
			logger.error(httpStatusEx.getMessage());
			throw new ExternalServiceInvocationException(MAP_GEOCODE_SERVICE, httpStatusEx.getRawStatusCode());
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			throw new ExternalServiceGatewayException(MAP_GEOCODE_SERVICE, ex);
		}
	}

}
