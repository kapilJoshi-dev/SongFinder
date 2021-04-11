package com.example.songfinder.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.songfinder.R
import com.example.songfinder.adapter.SongAdapter
import com.example.songfinder.models.Track
import com.example.songfinder.ui.SongActivity
import com.example.songfinder.ui.SongViewModel
import com.example.songfinder.util.Resource
import kotlinx.android.synthetic.main.fragment_search_song.*

class SearchSongFragment: Fragment(R.layout.fragment_search_song) {

    lateinit var viewModel: SongViewModel
    lateinit var songAdapter: SongAdapter
    lateinit var sortedTracks: List<Track>
    lateinit var menu: Menu

    val TAG = "SearchSongFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as SongActivity).viewModel

        setupRecyclerView()

        songAdapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("track", it)
            }
            findNavController().navigate(
                R.id.action_searchSongFragment_to_songDetailFragment,
                bundle
            )
        }

        searchButton.setOnClickListener {
            if (etSearch.text.toString().isNotEmpty()){
                viewModel.searchSongs(etSearch.text.toString())
            }else{
                Toast.makeText(context, "Please enter song name.", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.searchSong.observe(viewLifecycleOwner, Observer {response ->
            when(response){
                is Resource.Success -> {
                    hideProgressBar()
                    setHasOptionsMenu(true)
                    response.data?.let {songResponse ->
                        sortedTracks = viewModel.sortTrackDescending(songResponse
                            .results.trackmatches.track)
                        songAdapter.differ.submitList(sortedTracks)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let {message ->
                        Log.e(TAG, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        this.menu = menu
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.over_flow_menu, menu)
        menu.findItem(R.id.desc).isVisible = false
    }

    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        return when (item.itemId){
            R.id.asc -> {
                songAdapter.differ.submitList(
                    viewModel.sortTrackAscending(sortedTracks.toMutableList()))
                menu.findItem(R.id.desc).isVisible = true
                menu.findItem(R.id.asc).isVisible = false
                true
            }

            R.id.desc -> {
                songAdapter.differ.submitList(
                    viewModel.sortTrackDescending(sortedTracks.toMutableList()))
                menu.findItem(R.id.desc).isVisible = false
                menu.findItem(R.id.asc).isVisible = true
                true
            }

            else -> false
        }
    }

    private fun hideProgressBar(){
        paginationProgressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar(){
        paginationProgressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView(){
        songAdapter = SongAdapter()
        rvSearchSongs.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}