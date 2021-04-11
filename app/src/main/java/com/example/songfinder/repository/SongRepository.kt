package com.example.songfinder.repository

import com.example.songfinder.api.RetrofitInstance

class SongRepository {

    suspend fun searchSongs(searchQuery: String, pageNumber: Int) =
        RetrofitInstance.api.searchSongs(searchQuery = searchQuery, pageNumber = pageNumber)
}