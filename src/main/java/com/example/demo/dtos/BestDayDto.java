package com.example.demo.dtos;

import com.example.demo.enums.LocationNames;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BestDayDto {
    private LocationNames name;
    private Double min_temp;
    private Double wind_spd;

}
