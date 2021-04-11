package com.example.songfinder.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.songfinder.R
import com.example.songfinder.models.Track
import kotlinx.android.synthetic.main.item_song.view.*

class SongAdapter : RecyclerView.Adapter<SongAdapter.SongViewHolder>(){

    inner class SongViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    // in order to find any difference in the data for the recycler view
    private val differCallback = object : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem == newItem
        }
    }

    // takes two list and calculates the differences will run in the back ground
    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_song, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val track = differ.currentList[position]
        holder.itemView.apply {
            tvName.text = track.name
            tvArtist.text = "Artist: ${track.artist}"
            tvListener.text = "Listener: ${track.listeners}"
            setOnClickListener{
                onItemClickListener?.let {
                    it(track)
                }
            }
        }
    }

    private var onItemClickListener: ((Track) -> Unit)? = null

    fun setOnItemClickListener(listener: (Track) -> Unit) {
        onItemClickListener = listener
    }
}