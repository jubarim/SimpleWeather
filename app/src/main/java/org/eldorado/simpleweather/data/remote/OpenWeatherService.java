package org.eldorado.simpleweather.data.remote;

import org.eldorado.simpleweather.data.model.Weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Access https://openweathermap.org/current
 */
public interface OpenWeatherService {
    /**
     * Declares an HTTP GET request
     */
    @GET(RemoteContract.GET_WEATHER)
    Call<Weather> getCityWeather(
            @Query(RemoteContract.CITY_KEY) String cityName,
            @Query(RemoteContract.UNIT_KEY) String units,
            @Query(RemoteContract.ACCESS_KEY) String accessKey
    );
}
