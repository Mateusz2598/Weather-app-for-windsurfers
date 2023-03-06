package com.example.demo.service;

import com.example.demo.dtos.DataWeatherDto;
import com.example.demo.enums.LocationNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class WeatherService {

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
