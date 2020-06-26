package org.eldorado.simpleweather.data.remote

class RemoteWeatherDataSource (private val openWeatherService: OpenWeatherService) {

    fun getCityWeather(cityName: String) =
            openWeatherService.getCityWeather(
                    cityName,
                    RemoteContract.UNIT_METRIC,
                    RemoteContract.ACCESS_KEY_API_LAYER
            )
}
