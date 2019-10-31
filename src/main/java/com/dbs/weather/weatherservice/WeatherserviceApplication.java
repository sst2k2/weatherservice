package com.dbs.weather.weatherservice;

import com.dbs.weather.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;



@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages={"com.dbs.weather","templates"})
@EnableMongoRepositories(basePackageClasses = WeatherRepository.class)
public class WeatherserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherserviceApplication.class, args);
    }

    @Bean
    public RestTemplate getRestTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
