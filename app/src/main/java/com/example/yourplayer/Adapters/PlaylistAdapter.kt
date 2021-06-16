package com.example.yourplayer.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.yourplayer.Fragments.PlaylistFragment
import com.example.yourplayer.Models.SongData
import com.example.yourplayer.R
import com.example.yourplayer.ViewModels.PlaylistViewModel
import kotlinx.android.synthetic.main.songs_layout.view.*

class PlaylistAdapter(val list: ArrayList<SongData>, val context: PlaylistFragment, val playlistViewModel: PlaylistViewModel) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>() {


    var currentPosition: Int = 0

    class PlaylistViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        return PlaylistViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.songs_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        holder.view.name.text = list[position].name
        holder.view.artist.text = list[position].artist

        playlistViewModel.currentIndex.observe(context, Observer {
            if (currentPosition != it) {
                currentPosition = it
            }

        })
        if (currentPosition != position) {
            holder.view.img.setImageResource(R.drawable.music_icon)
            holder.view.name.setTextColor(Color.parseColor("#FFF5FD"))
        }else{
            holder.view.img.setImageResource(R.drawable.playing)
            holder.view.name.setTextColor(Color.parseColor("#FF968D"))
        }

        holder.view.song.setOnClickListener {

            holder.view.img.setImageResource(R.drawable.playing)
            holder.view.name.setTextColor(Color.parseColor("#FF968D"))
            currentPosition = position
            playlistViewModel.setCurrentIndex(currentPosition)
            playlistViewModel.setCurrentSong(list[currentPosition])
            playlistViewModel.playSong(list[currentPosition])
            notifyDataSetChanged()
        }
    }

    override fun getItemCount() = list.size
}