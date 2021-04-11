package com.example.songfinder.ui.fragments

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.songfinder.R
import com.example.songfinder.ui.SongActivity
import com.example.songfinder.ui.SongViewModel
import kotlinx.android.synthetic.main.fragment_song_detail.*

class SongDetailFragment: Fragment(R.layout.fragment_song_detail) {

    lateinit var viewModel: SongViewModel
    val args: SongDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as SongActivity).viewModel

        val track = args.track
        webView.apply {
            webViewClient = WebViewClient()
            loadUrl(track.url)
        }
    }
}