package com.example.songfinder.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.songfinder.models.SongResponse
import com.example.songfinder.models.Track
import com.example.songfinder.repository.SongRepository
import com.example.songfinder.util.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class SongViewModel(
    val songRepository: SongRepository
):ViewModel(){

    val searchSong: MutableLiveData<Resource<SongResponse>> = MutableLiveData()
    var searchSongPage = 1

    fun searchSongs(searchQuery: String) = viewModelScope.launch {
        searchSong.postValue(Resource.Loading())
        val response = songRepository.searchSongs(searchQuery, searchSongPage)
        searchSong.postValue(handleSearchSongResponse(response))
    }

    private fun handleSearchSongResponse(response: Response<SongResponse>): Resource<SongResponse> {
        if(response.isSuccessful){
            response.body()?.let {resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    fun sortTrackDescending(tracks: MutableList<Track>):List<Track>{
        return tracks.sortedByDescending { it.listeners.toInt() }
    }

    fun sortTrackAscending(tracks: MutableList<Track>):List<Track>{
        return tracks.sortedBy { it.listeners.toInt() }
    }

}