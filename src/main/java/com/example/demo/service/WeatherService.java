package com.example.demo.service;

import com.example.demo.dtos.BestDayDto;
import com.example.demo.dtos.DataWeatherDto;
import com.example.demo.enums.LocationNames;
import com.example.demo.exception.WeatherException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class WeatherService {

    public BestDayDto findLocation(LocalDate date) {
        if (date.isBefore(LocalDate.now()) || date.isAfter(date.plusDays(7))) {
            throw new WeatherException("WRONG VALUE. ENTER THE DATE FROM " + LocalDate.now() + " TO " + LocalDate.now().plusDays(7));
        }
        List<DataWeatherDto> list = new ArrayList<>();
        List.of(LocationNames.values()).stream()
                .forEach(o -> {
                    List<DataWeatherDto> supportingList = getListGoodDays(o);
                    list.addAll(supportingList);
                });

        DataWeatherDto dataWeatherDto = list.stream()
                .filter(o -> o.getDate().compareTo(date) == 0)
                .max(Comparator.comparing(o -> o.getWind_spd() * 3 + o.getTemp()))
                .orElse(new DataWeatherDto());

        return new BestDayDto(dataWeatherDto.getLocationNames(), dataWeatherDto.getMin_temp(), dataWeatherDto.getWind_spd());
    }

    public BestDayDto findWeather(LocalDate date, LocationNames locationNames) {
        if (date.isBefore(LocalDate.now()) || date.isAfter(date.plusDays(7))) {
            throw new WeatherException("WRONG VALUE. ENTER THE DATE FROM " + LocalDate.now() + " TO " + LocalDate.now().plusDays(7), LocalDate.now());
        }
        DataWeatherDto dataWeatherDto = getListGoodDays(locationNames).stream()
                .filter(o -> o.getDate().compareTo(date) == 0)
                .max(Comparator.comparing(o -> o.getWind_spd() * 3 + o.getTemp()))
                .orElse(new DataWeatherDto());

        return new BestDayDto(dataWeatherDto.getLocationNames(), dataWeatherDto.getMin_temp(), dataWeatherDto.getWind_spd());
    }

    public List<DataWeatherDto> getListGoodDays(LocationNames locationNames) {

        URL urlJastarnia;
        URL urlBridgetown;
        URL urlFortaleza;
        URL urlProtaras;
        URL urlSurinam;
        try {
            urlJastarnia = new URL("https://api.weatherbit.io/v2.0/forecast/daily?city_id=3097421&key=1704416c90e14d18874c1e499efac899\n");
            urlBridgetown = new URL("https://api.weatherbit.io/v2.0/forecast/daily?city_id=4507067&key=1704416c90e14d18874c1e499efac899\n");
            urlFortaleza = new URL("https://api.weatherbit.io/v2.0/forecast/daily?city_id=3399415&key=1704416c90e14d18874c1e499efac899\n");
            urlProtaras = new URL("https://api.weatherbit.io/v2.0/forecast/daily?city_id=18918&key=1704416c90e14d18874c1e499efac899\n");
            urlSurinam = new URL("https://api.weatherbit.io/v2.0/forecast/daily?city_id=933988&key=1704416c90e14d18874c1e499efac899\n");
        } catch (MalformedURLException e) {
            throw new RuntimeException("INVALID URL. KEY EXPIRED.");
        }

        switch (locationNames) {
            case JASTARNIA:
                return createListGoodWeather(urlJastarnia, locationNames);
            case BRIDGETOWN:
                return createListGoodWeather(urlBridgetown, locationNames);
            case FORTALEZA:
                return createListGoodWeather(urlFortaleza, locationNames);
            case PROTARAS:
                return createListGoodWeather(urlProtaras, locationNames);
            case SURINAM:
                return createListGoodWeather(urlSurinam, locationNames);
        }
        return null;
    }

    public List<DataWeatherDto> createListGoodWeather(URL url, LocationNames locationNames) {
        List<DataWeatherDto> list = new ArrayList<>();
        try {
            Files.write(Paths.get("weatherForSevenDays.json"), new Scanner(url.openStream()).nextLine().getBytes());
            JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(Paths.get("weatherForSevenDays.json"))));
            JSONArray dataArray = jsonObject.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject dataObject = dataArray.getJSONObject(i);
                Double wind_spd = dataObject.getDouble("wind_spd");
                Double temp = dataObject.getDouble("temp");
                if (wind_spd >= 5 && wind_spd <= 18 && temp >= 5 && temp <= 35) {
                    String dateString = dataObject.getString("datetime");
                    LocalDate datetime = LocalDate.parse(dateString, DateTimeFormatter.ISO_DATE);
                    double min_temp = dataObject.getDouble("min_temp");
                    list.add(new DataWeatherDto(locationNames, datetime, min_temp, wind_spd, temp));
                }
            }

        } catch (JSONException e) {
            throw new RuntimeException("VALUE RECEIVED IS INCORRECT.");
        } catch (IOException e) {
            throw new RuntimeException("PROBLEM SCANNING OR READING FROM \"weatherForSevenDays.json\" FILE");
        }
        return list;
    }
}
