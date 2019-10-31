package com.dbs.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Forecast {

	private int longitude;
	private int latitude;
	private String timezone;
	private int offset;
	private CurrentForecast currently;
	
	private String formattedAddress;
	private String searchAddress;
	
	public String getSearchAddress() {
		return searchAddress;
	}
	public void setSearchAddress(String searchAddress) {
		this.searchAddress = searchAddress;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	
	public int getLongitude() {
		return longitude;
	}
	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}
	public int getLatitude() {
		return latitude;
	}
	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}
	public String getTimezone() {
		return timezone;
	}
	public void setTimezone(String timeZone) {
		this.timezone = timeZone;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public CurrentForecast getCurrently() {
		return currently;
	}
	public void setCurrently(CurrentForecast currently) {
		this.currently = currently;
	}

}
