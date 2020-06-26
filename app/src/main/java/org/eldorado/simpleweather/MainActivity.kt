package org.eldorado.simpleweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import org.eldorado.simpleweather.data.injection.RemoteModule
import org.eldorado.simpleweather.data.model.Weather
import org.eldorado.simpleweather.data.remote.OpenWeatherService
import org.eldorado.simpleweather.data.remote.RemoteWeatherDataSource
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var weatherService: OpenWeatherService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeRetrofit()

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
        private const val TAG = "SimpleWeather.Main"

        private const val city1 = "Brasilia"
    }
}
