package org.eldorado.simpleweather.data.model

data class WeatherData(
    val temp: Double = 0.0,
    val tempMin: Double = 0.0,
    val humidity: Int = 0,
    val pressure: Int = 0,
    val tempMax: Double = 0.0
)