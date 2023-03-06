package com.example.demo.dtos;

import com.example.demo.enums.LocationNames;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataWeatherDto {
    private LocationNames locationNames;
    private LocalDate date;
    private Double min_temp;
    private Double wind_spd;
    private Double temp;
}
