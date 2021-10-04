package org.eldorado.simpleweather.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import org.eldorado.simpleweather.R;
import org.eldorado.simpleweather.data.injection.RemoteModule;
import org.eldorado.simpleweather.data.model.Weather;
import org.eldorado.simpleweather.data.model.WeatherInfo;
import org.eldorado.simpleweather.data.remote.OpenWeatherService;
import org.eldorado.simpleweather.data.remote.RemoteWeatherDataSource;
import org.eldorado.simpleweather.databinding.FragmentWeatherBinding;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WeatherListFragment extends Fragment {
    private OpenWeatherService weatherService;

    private static final String TAG = "WeatherListFragment";
    private static final String city1 = "Brasilia";

    private FragmentWeatherBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        initializeRetrofit();

        binding = FragmentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getCityWeather(city1);
    }

    private void initializeRetrofit() {
        OkHttpClient client = RemoteModule.provideOkHttpClient();
        Gson gson = RemoteModule.provideGson();
        Retrofit retrofit = RemoteModule.provideRetrofit(gson, client);
        weatherService = RemoteModule.provideOpenWeatherService(retrofit);
    }

    private void getCityWeather(final String city) {
        RemoteWeatherDataSource remoteWeatherDataSource = RemoteWeatherDataSource.getInstance(weatherService);

        Call<Weather> call = remoteWeatherDataSource.getCityWeather(city);

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(@NonNull Call<Weather> call, @NonNull Response<Weather> response) {
                final Weather weather = response.body();

                Log.d(TAG, "Obtained weather: " + weather);

                if (weather != null) {
                    String description = getDescription(weather.getWeather().get(0));

                    binding.hello.setText(getResources().getString(
                            R.string.weather_result,
                            city,
                            weather.getMain().getTemp(),
                            description
                    ));
                }
            }

            @Override
            public void onFailure(@NonNull Call<Weather> call, @NonNull Throwable t) {
                Snackbar.make(
                        binding.hello,
                        "Error obtaining temperature",
                        Snackbar.LENGTH_LONG
                ).show();
            }
        });

    }

    private String getDescription(WeatherInfo weatherInfo) {
        if (weatherInfo != null) {
            return weatherInfo.getDescription();
        } else {
            return "";
        }
    }
}
