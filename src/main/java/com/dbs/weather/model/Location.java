
package com.dbs.weather.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {

    @JsonProperty("latLng")
    private LatLng latLng;

    @JsonProperty("latLng")
    public LatLng getLatLng() {
        return latLng;
    }

    @JsonProperty("latLng")
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

}