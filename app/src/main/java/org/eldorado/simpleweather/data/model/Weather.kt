package org.eldorado.simpleweather.data.model

// API reference: https://openweathermap.org/current
data class Weather(
    val dt: Int = 0,
    val coord: Coordinates,
    val visibility: Int = 0,
    val weather: List<WeatherInfo>?,
    val name: String = "",
    val cod: Int = 0,
    val main: WeatherData,
    val clouds: Clouds,
    val id: Int = 0,
    val sys: Sys,
    val base: String = "",
    val wind: Wind
)