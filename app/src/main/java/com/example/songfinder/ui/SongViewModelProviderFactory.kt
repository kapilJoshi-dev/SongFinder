package com.example.songfinder.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.songfinder.repository.SongRepository

class SongViewModelProviderFactory(
    val songRepository: SongRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SongViewModel(songRepository) as T
    }
}