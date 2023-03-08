The weather app for windsurfers.
===
The application is written in Java and based on the concepts of
Object-Oriented Programming. It uses libraries to process JSON.

The Weather Application for Windsurfers is an international service for windsurfers
that collects weather data for the next seven days from five locations:

Jastarnia (Poland),
Bridgetown (Barbados),
Fortaleza (Brazil),
Protaras (Cyprus)
Suriname (Mauritius).

The data is saved in a JSON file and is used to determine
the best place for water sports.

The criteria that are used to select the location are temperature
between 5-18°C and wind speed between 5-18 m/s.
To determine the best location for water sports, the wind speed is
multiplied by 3 and added to the temperature.
If no locations match these criteria, a null result will be returned.

The application is written in Java and is based on the concepts of
object-oriented programming. It uses libraries to process
JSON data, charts and visualizations.

## Endpoints :
#### 1. If the conditions are good on a given day and location, it returns an object, otherwise it returns null.
```
Http request in Postman : 
@GetMapping    http://localhost:8080/weather

Postman default body in JSON : 
{
    "name": "BRIDGETOWN",
    "min_temp": 8.9,
    "wind_spd": 7.5
}
```
#### 2. Returns the best location for water sports on a given day out of all possible.
```
Http request in Postman : 
@GetMapping    http://localhost:8080/weather/location

Postman default body in JSON : 
{
    "name": "SURINAM",
    "min_temp": 23.6,
    "wind_spd": 5.1
}
```