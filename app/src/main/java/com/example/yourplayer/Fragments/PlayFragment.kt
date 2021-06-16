package com.example.yourplayer.Fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.yourplayer.R
import com.example.yourplayer.ViewModels.PlaylistViewModel
import kotlinx.android.synthetic.main.fragment_play.*


class PlayFragment : Fragment() {

    val viewModel : PlaylistViewModel by activityViewModels()
    private lateinit var seekBar : SeekBar
    private lateinit var handler: Handler
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        val playPause = view.findViewById<ImageView>(R.id.play_pause_btn)
        val name = view.findViewById<TextView>(R.id.song_name)
        val artist = view.findViewById<TextView>(R.id.song_artist)
        val next = view.findViewById<ImageView>(R.id.next)
        val prev = view.findViewById<ImageView>(R.id.previous)
        seekBar = view.findViewById<SeekBar>(R.id.seek_bar)




//        seekBar.max = viewModel.mp.duration/1000
//        Runnable {
//            seekBar.progress = viewModel.mp.currentPosition/1000
//        }
        handler = Handler()
        viewModel.currentSong.observe(viewLifecycleOwner, Observer {
            name.text = it.name
            artist.text = it.artist
            updateSeekBar()
        })

        viewModel.duration.observe(viewLifecycleOwner, Observer {
            seekBar.max = it
        })
        viewModel.play.observe(viewLifecycleOwner, Observer {
            if (it){
                playPause.setImageResource(R.drawable.pause_icon)
            }else{
                playPause.setImageResource(R.drawable.play_icon)
            }
        })


        playPause.setOnClickListener {
            viewModel.handlePlayPause()
        }

        next.setOnClickListener {
            viewModel.nextSong()
        }

        prev.setOnClickListener {
            viewModel.previousSong()
        }


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    viewModel.mp.seekTo(progress)
//                    viewModel.mp.seekTo(progress)
                    seekBar?.progress = progress
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }

        })

        return view
    }

//    override fun onPause() {
//        viewModel.pauseSong()
//        super.onPause()
//    }
//
//    override fun onResume() {
//        viewModel.resumeSong()
//        super.onResume()
//    }

    fun updateSeekBar() {
        val currPos = viewModel.mp.currentPosition

        seekBar.progress = currPos
        val runnable = Runnable {
            updateSeekBar()
        }
        handler.postDelayed(runnable, 1000)
    }
}