package com.example.yourplayer.Fragments

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.example.yourplayer.R
import com.example.yourplayer.ViewModels.PlaylistViewModel


class PlayFragment : Fragment() {

    val viewModel : PlaylistViewModel by activityViewModels()
    private lateinit var seekBar : SeekBar
    private lateinit var handler: Handler
    private lateinit var playPause : ImageView
    private lateinit var finalnTime : TextView
    private lateinit var initialTime : TextView
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_play, container, false)
        playPause = view.findViewById(R.id.play_pause_btn)
        val name = view.findViewById<TextView>(R.id.song_name)
        val artist = view.findViewById<TextView>(R.id.song_artist)
        val next = view.findViewById<ImageView>(R.id.next)
        val prev = view.findViewById<ImageView>(R.id.previous)
        val back = view.findViewById<LinearLayout>(R.id.back_btn)
        initialTime = view.findViewById<TextView>(R.id.initial_time)
        finalnTime = view.findViewById(R.id.final_time)
        seekBar = view.findViewById(R.id.seek_bar)


        back.setOnClickListener {
            activity?.onBackPressed()
        }

        handler = Handler()
        viewModel.currentSong.observe(viewLifecycleOwner, Observer {
            name.text = it.name
            artist.text = it.artist
            updateSeekBar()
        })

        viewModel.duration.observe(viewLifecycleOwner, Observer {
            seekBar.max = it
            finalnTime.text = getTimeString(it.toLong())
        })
        viewModel.play.observe(viewLifecycleOwner, Observer {
            if (it) {
                playPause.setImageResource(R.drawable.pause_icon)
            } else {
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


        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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


    fun updateSeekBar() {
        val currPos = viewModel.mp.currentPosition

        seekBar.progress = currPos
        val runnable = Runnable {
            updateSeekBar()
            initialTime.text = getTimeString(viewModel.mp.currentPosition.toLong())
        }
        handler.postDelayed(runnable, 1000)
    }

    private fun getTimeString(millis: Long): String? {
        val buf = StringBuffer()
        val minutes = (millis % (1000 * 60 * 60) / (1000 * 60)).toInt()
        val seconds = (millis % (1000 * 60 * 60) % (1000 * 60) / 1000).toInt()
        buf

                .append(String.format("%02d", minutes))
                .append(":")
                .append(String.format("%02d", seconds))
        return buf.toString()
    }

}