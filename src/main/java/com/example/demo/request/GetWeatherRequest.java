package com.example.demo.request;

import com.example.demo.enums.LocationNames;
import lombok.Data;

import java.time.LocalDate;

@Data
public class GetWeatherRequest {
    private LocalDate date;
    private LocationNames name;
}
