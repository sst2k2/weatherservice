package com.dbs.weather.weatherservice;

import com.dbs.weather.controller.WeatherController;
import com.dbs.weather.entities.ForecastEntity;
import com.dbs.weather.repository.WeatherRepository;
import com.dbs.weather.service.MapInfoRetrieverImpl;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class WeatherserviceApplicationTests {

    @Autowired
    private WeatherController weatherController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WeatherRepository repository;

    @Value("map.base.url")
    private String mapQuestUrl;


    @Test
    public void contextLoads() throws Exception{
        assertThat(weatherController).isNotNull();
        assertThat(repository).isNotNull();
    }

    @Test
    public void shouldReturnDefaultMessage() throws Exception{
        this.mockMvc.perform(get("/weather")).andExpect(status().isOk());
    }

    @Test
    public void checkMongoDbConnection() throws Exception{
        assertThat(repository.findAll()).isNotEmpty();
    }

}
