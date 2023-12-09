package com.android.gotripmap.data.network

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TestAPIFactory {
  private const val TEST_SERVER_IP = "http://192.168.43.177:3001" //для отладки
  private val retrofit = Retrofit.Builder()
    .addConverterFactory(
      GsonConverterFactory.create(
        GsonBuilder()
          .setLenient()
          .create()
      )
    )
    .baseUrl(TEST_SERVER_IP)
    .build()

  val testApiService = retrofit.create(TestApiService::class.java)

}
