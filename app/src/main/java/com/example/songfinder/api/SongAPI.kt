package com.example.songfinder.api

import com.example.songfinder.models.SongResponse
import com.example.songfinder.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SongAPI {

    @GET("2.0/")
    suspend fun searchSongs(
        @Query("method")
        searchMethod: String = "track.search",
        @Query("track")
        searchQuery: String,
        @Query("format")
        dataFormat: String ="json",
        @Query("page")
        pageNumber: Int = 1,
        @Query("api_key")
        apiKey: String = API_KEY
    ): Response<SongResponse>
}