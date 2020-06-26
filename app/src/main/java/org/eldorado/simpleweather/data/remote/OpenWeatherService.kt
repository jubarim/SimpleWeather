package org.eldorado.simpleweather.data.remote

import org.eldorado.simpleweather.data.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Access https://openweathermap.org/current
 */
interface OpenWeatherService {
    /**
     * @GET declares an HTTP GET request
     */
    @GET(RemoteContract.GET_WEATHER)
    fun getCityWeather(
            @Query(RemoteContract.CITY_KEY) cityName: String,
            @Query(RemoteContract.UNIT_KEY) units: String,
            @Query(RemoteContract.ACCESS_KEY) accessKey: String
    ): Call<Weather>
}
