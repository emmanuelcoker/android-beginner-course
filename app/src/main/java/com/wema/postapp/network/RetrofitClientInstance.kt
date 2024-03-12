package com.wema.postapp.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClientInstance {
    private val BASE_URL: String = "https://jsonplaceholder.typicode.com/"
    private var retrofitBuilder: Retrofit? = null

    constructor(){
        //setup okHttp builder
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.readTimeout(3, TimeUnit.MINUTES)
        okHttpBuilder.connectTimeout(3, TimeUnit.MINUTES)

        //setup retrofit builder
        retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //create singleton to create the instance
    companion object {
        private var retrofitInstance: RetrofitClientInstance? = null
        fun getInstance(): RetrofitClientInstance? {
            if (retrofitInstance == null) {
                    retrofitInstance =  RetrofitClientInstance()
            }
            return retrofitInstance
        }
    }


    //reset retrofit instance
    fun reset() {
        retrofitInstance = null
        getInstance()
    }

    //call service to get data: build the retrofit instance with the api
    fun getDataService(): GetDataService? {
        return retrofitBuilder?.create(GetDataService::class.java)
    }

}