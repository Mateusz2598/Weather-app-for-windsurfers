package com.example.demo.controller;

import com.example.demo.dtos.BestDayDto;
import com.example.demo.request.GetWeatherRequest;
import com.example.demo.service.WeatherService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/weather")
public class WeatherAppController {
    private final WeatherService weatherService;

    public WeatherAppController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping()
    public BestDayDto findWeather(@RequestBody GetWeatherRequest request) {

        return weatherService.findWeather(request.getDate(), request.getName());
    }

    @GetMapping("/location")
    public BestDayDto findLocation(@RequestParam LocalDate date) {

        return weatherService.findLocation(date);
    }
}
