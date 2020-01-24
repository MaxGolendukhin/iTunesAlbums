package com.golendukhin.itunesalbums.nework

import com.golendukhin.itunesalbums.grid.Results
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://itunes.apple.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(client)
    .build()

interface iTunesApiService {
    @GET("search")
    fun getAlbums(
        @Query("term") searchedAlbum: String,
        @Query("entity") album: String):
            Deferred<Results>
}

object iTunesApi {
    val retrofitService : iTunesApiService by lazy { retrofit.create(iTunesApiService::class.java) }
}