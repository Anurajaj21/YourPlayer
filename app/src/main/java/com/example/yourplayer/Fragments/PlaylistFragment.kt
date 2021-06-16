package com.example.yourplayer.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yourplayer.Adapters.PlaylistAdapter
import com.example.yourplayer.Models.SongData
import com.example.yourplayer.R
import com.example.yourplayer.ViewModels.PlaylistViewModel
import kotlinx.android.synthetic.main.fragment_playlist.*


class PlaylistFragment : Fragment() {

    val list = ArrayList<SongData>()

    val viewModel : PlaylistViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_playlist, container, false)
        val songRv = view.findViewById<RecyclerView>(R.id.songs_rv)
        val play = view.findViewById<ImageView>(R.id.play)
        val bottomLayout = view.findViewById<ConstraintLayout>(R.id.bottom_layout)
        setList()
        viewModel.setList()
        viewModel.setInitially()


        val adapter = PlaylistAdapter(list, this, viewModel)
        adapter.notifyDataSetChanged()
        songRv.adapter = adapter
        songRv.layoutManager = LinearLayoutManager(requireContext())
        songRv.setHasFixedSize(true)


        viewModel.currentSong.observe(viewLifecycleOwner, Observer {
            Log.d("currrentSong", it.toString())
            cp_song.text = it.name
            cp_song_artist.text = it.artist
        })

        viewModel.play.observe(viewLifecycleOwner, Observer {
            if (it){
                play.setImageResource(R.drawable.pause_icon)
            }else{
                play.setImageResource(R.drawable.play_icon)
            }
        })

        bottomLayout.setOnClickListener {
            val activity = it.context as AppCompatActivity
            val fragmentTransition = activity.supportFragmentManager.beginTransaction()
            fragmentTransition.replace(R.id.fragment_container, PlayFragment())
            fragmentTransition.setCustomAnimations(R.anim.fade_in, R.anim.slide_up, R.anim.fade_in, R.anim.slide_down)
            fragmentTransition.addToBackStack("playlist")
            fragmentTransition.commit()
        }

        play.setOnClickListener {
            viewModel.handlePlayPause()
            adapter.notifyDataSetChanged()
        }

        return view
    }



    fun setList(){
        list.clear()
        list.add(SongData("Har Ek Friend Kamina Hota Hai", "Unknown", R.raw.har_ek_friend_kamina_hota_hai))
        list.add(SongData("Hume Tumse Pyaar Kitna", "Unknown", R.raw.hume_tumse_pyaar_kitna))
        list.add(SongData("Believer", "Unknown", R.raw.believer))
        list.add(SongData("Brown Munde", "Unknown", R.raw.brown_munde))
        list.add(SongData("Casanova", "Unknown", R.raw.casanova))
        list.add(SongData("Half of Fame", "Unknown", R.raw.hall_of_fame))
        list.add(SongData("Lut Gye", "Unknown", R.raw.lut_gye))
        list.add(SongData("Paani Paani", "Unknown", R.raw.pani_pani))
        list.add(SongData("Shor Machega", "Unknown", R.raw.shor_machega))
        list.add(SongData("Tu Aake Dekh", "Unknown", R.raw.tu_aake_dekh))
    }

    override fun onPause() {
        viewModel.pauseSong()
        super.onPause()
    }

    override fun onResume() {
        viewModel.resumeSong()
        super.onResume()
    }
}