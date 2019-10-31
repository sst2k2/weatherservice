package com.dbs.weather.service;

import com.dbs.weather.model.MapAPIResponse;

public interface MapInfoRetriever {

	public MapAPIResponse getMapInfoFor(String city, String state);
	
}
