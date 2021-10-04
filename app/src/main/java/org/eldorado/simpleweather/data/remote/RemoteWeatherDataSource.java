package org.eldorado.simpleweather.data.remote;

import org.eldorado.simpleweather.data.model.Weather;

import retrofit2.Call;

public class RemoteWeatherDataSource {

    private final OpenWeatherService mOpenWeatherService;

    private RemoteWeatherDataSource(OpenWeatherService openWeatherService) {
        mOpenWeatherService = openWeatherService;
    }

    public static RemoteWeatherDataSource getInstance(OpenWeatherService openWeatherService) {
        return new RemoteWeatherDataSource(openWeatherService);
    }

    public Call<Weather> getCityWeather(String cityName) {
        return mOpenWeatherService.getCityWeather(
                cityName,
                RemoteContract.UNIT_METRIC,
                RemoteContract.ACCESS_KEY_API_LAYER
        );
    }
}
