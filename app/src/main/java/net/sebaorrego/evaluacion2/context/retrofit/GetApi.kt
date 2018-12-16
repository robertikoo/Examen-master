package net.sebaorrego.evaluacion2.context.retrofit

import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object GetApi {


    var BaseUrl = "http://pokeapi.co/api/v2/"

    private lateinit var retrofit: Retrofit

    val api: Retrofit
        get() {
            val client = OkHttpClient.Builder()
                .connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .build()

            retrofit = Retrofit.Builder().baseUrl(BaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()).build()

            return retrofit
        }

}