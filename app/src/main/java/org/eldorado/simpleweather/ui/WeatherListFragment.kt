package org.eldorado.simpleweather.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_weather.*
import org.eldorado.simpleweather.R
import org.eldorado.simpleweather.data.injection.RemoteModule
import org.eldorado.simpleweather.data.model.Weather
import org.eldorado.simpleweather.data.remote.OpenWeatherService
import org.eldorado.simpleweather.data.remote.RemoteWeatherDataSource
import retrofit2.Callback
import retrofit2.Response

class WeatherListFragment : Fragment() {
    private lateinit var weatherService: OpenWeatherService

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        initializeRetrofit()

        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getCityWeather(city1)
    }

    private fun initializeRetrofit() {
        val client = RemoteModule.provideOkHttpClient()
        val gson = RemoteModule.provideGson()
        val retrofit = RemoteModule.provideRetrofit(gson, client)

        weatherService = RemoteModule.provideOpenWeatherService(retrofit)
    }

    private fun getCityWeather(city: String) {
        val call = RemoteWeatherDataSource(weatherService).getCityWeather(city)

        call.enqueue(object : Callback<Weather> {
            override fun onFailure(call: retrofit2.Call<Weather>, t: Throwable) {
                Snackbar.make(
                    hello,
                    "Error obtaining temperature",
                    Snackbar.LENGTH_LONG
                ).show()
            }

            override fun onResponse(call: retrofit2.Call<Weather>, response: Response<Weather>) {
                val weather = response.body()

                Log.d(TAG, "Obtained weather: $weather")

                // In this demo, we only replace the hello word string with the result
                weather?.let {
                    hello.text = resources.getString(
                        R.string.weather_result,
                        city,
                        weather.main.temp,
                        weather.weather?.get(0)?.description
                    )
                }
            }
        })
    }

    companion object {
        private const val TAG = "WeatherListFragment"

        private const val city1 = "Brasilia"
    }
}