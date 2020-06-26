package org.eldorado.simpleweather.data.injection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.eldorado.simpleweather.data.remote.OpenWeatherService
import org.eldorado.simpleweather.data.remote.RemoteContract
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteModule {
    fun provideGson(): Gson =
            GsonBuilder()
                    .setLenient()
                    .create()

    fun provideOkHttpClient(): OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .build()

    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .baseUrl(RemoteContract.BASE_API_LAYER)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient)
                    .build()

    fun provideOpenWeatherService(retrofit: Retrofit): OpenWeatherService =
            retrofit.create(OpenWeatherService::class.java)
}
