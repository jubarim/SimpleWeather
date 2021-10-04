package org.eldorado.simpleweather.data.injection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.eldorado.simpleweather.data.remote.OpenWeatherService;
import org.eldorado.simpleweather.data.remote.RemoteContract;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteModule {

    public static Gson provideGson() {
        return new GsonBuilder().setLenient().create();
    }

    public static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(
                        new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public static Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(RemoteContract.BASE_API_LAYER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build();
    }

    public static OpenWeatherService provideOpenWeatherService(Retrofit retrofit) {
        return retrofit.create(OpenWeatherService.class);
    }
}
