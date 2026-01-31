# Weather App — Assignment 7 (Data & Networking)

Native Mobile Development  
SE-2422 Chepurnenko Sergey

---

##  Project Overview

This project is a simple **Weather Application** developed in **Kotlin** using **Android Studio** and **Jetpack Compose**.

The app allows users to search for a city and view:

- Current weather conditions
- 3-day forecast
- Offline cached data (last successful response)
- Unit switching (Celsius / Fahrenheit)

The project was implemented following the requirements of **Assignment 7 — API Integration & Local Storage**.

---

##  Features Implemented

###  City Search
- User can enter any city name in the search field
- Input validation is included (empty input is not allowed)
- Error message is shown if the city is not found

---

###  Current Weather Screen
The app displays the following required weather data:

- City name
- Temperature
- Weather condition (mapped from weather code)
- Humidity
- Wind speed
- Last update time
- Daily min/max temperatures

---

###  Forecast Feature
- Daily forecast is displayed for at least **3 days**
- Each forecast card shows:
    - Date
    - Min temperature
    - Max temperature

---

###  Settings Screen
The app supports temperature unit switching:

- Celsius (°C)
- Fahrenheit (°F)

Changes apply instantly without restarting the app.

---

###  Offline Mode + Local Cache
The app stores the last successful weather response using **SharedPreferences**.

If the internet is unavailable:

- Cached weather is shown instead
- A clear warning label is displayed:

OFFLINE MODE: Showing cached weather for last saved city

This ensures correct user experience even without network access.

---

###  Error Handling
The application gracefully handles:

- Empty input
- Invalid city name
- Network failures / no internet
- API errors when no cached data exists

The UI never crashes and always shows informative messages.

---

##  API Used

This project uses the **Open-Meteo API** (no API key required).

### 1. Geocoding (City → Coordinates)

https://geocoding-api.open-meteo.com/v1/search?name=Astana


### 2. Weather Forecast Request

https://api.open-meteo.com/v1/forecast
?latitude=...
&longitude=...
&current_weather=true
&daily=temperature_2m_max,temperature_2m_min
&hourly=relativehumidity_2m
&timezone=auto

---

## Architecture

The project follows **MVVM + Repository Pattern**:

### Data Layer
- Retrofit API services
- Repository handles networking + caching

### ViewModel Layer
- State management with `StateFlow`
- Handles loading, errors, offline state

### UI Layer
- Built using Jetpack Compose
- Screens separated by responsibility:
    - SearchScreen
    - WeatherScreen
    - SettingsScreen

---

## Local Persistence

SharedPreferences is used to store:

- Last successful weather JSON response
- Last city name

This enables offline support.

---

## How to Run

1. Clone the repository
2. Open the project in Android Studio
3. Sync Gradle dependencies
4. Run on emulator or Android device (SDK 24+)

---

## Known Limitations

- Only the last searched city is cached
- No dropdown suggestions or search history implemented (optional feature)

---

## Academic Integrity Note

This project was developed as an original student work.  
External resources used:

- Open-Meteo API documentation
- AI assistance (ChatGPT) for guidance and debugging  
  All code was reviewed, modified, and integrated manually.

---