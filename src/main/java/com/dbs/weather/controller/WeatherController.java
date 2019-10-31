package com.dbs.weather.controller;

import com.dbs.weather.entities.ForecastEntity;
import com.dbs.weather.service.ForecastRetriever;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WeatherController {

	@Autowired
	private ForecastRetriever forecastRetriever;

	private static Logger logger = LoggerFactory.getLogger(WeatherController.class);

    @RequestMapping(value = "/weather", method = RequestMethod.GET)
    public ModelAndView getWeatherPage() {
    	logger.info("GET: /weather");
		ModelAndView mav=new ModelAndView("weather");
		List<ForecastEntity> forecast = forecastRetriever.getForecasts();
		mav.addObject("weather",forecast);
		return mav;
    }

}
