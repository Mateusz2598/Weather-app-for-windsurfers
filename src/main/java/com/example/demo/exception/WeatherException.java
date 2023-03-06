package com.example.demo.exception;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WeatherException extends RuntimeException {
    String mesage;
    LocalDate date;

    public WeatherException(String mesage, LocalDate date) {
        this.mesage = mesage;
        this.date = date;
    }

    public WeatherException(String mesage) {
        this.mesage = mesage;
    }

}
