package com.example.songfinder.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.songfinder.R
import com.example.songfinder.repository.SongRepository
import kotlinx.android.synthetic.main.activity_song.*

class SongActivity : AppCompatActivity() {

    lateinit var viewModel: SongViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_song)

        val songRepository = SongRepository()
        val viewModelProviderFactory = SongViewModelProviderFactory(songRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(SongViewModel::class.java)
    }
}