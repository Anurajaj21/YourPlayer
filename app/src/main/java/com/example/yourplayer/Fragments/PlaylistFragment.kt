package com.example.yourplayer.Fragments

import android.os.Bundle
import android.os.Handler
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
import kotlinx.android.synthetic.main.fragment_play.*
import kotlinx.android.synthetic.main.fragment_playlist.*


class PlaylistFragment : Fragment() {

    val list = ArrayList<SongData>()
    val viewModel: PlaylistViewModel by activityViewModels()

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
            if (it) {
                play.setImageResource(R.drawable.pause_icon)
            } else {
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

//        viewModel.setInitially()
        return view
    }



    fun setList() {
        list.clear()
        list.add(SongData("Believer", "Imagine Dragons", R.raw.believer))
        list.add(SongData("Hume Tumse Pyaar Kitna", "Sanam, R. D. Burman", R.raw.hume_tumse_pyaar_kitna))
        list.add(SongData("Brown Munde", "AP Dhillon", R.raw.brown_munde))
        list.add(SongData("Casanova", "King, Rahul Sathu", R.raw.casanova))
        list.add(SongData("Hall of Fame", "The Script", R.raw.hall_of_fame))
        list.add(SongData("Lut Gye", "Jubin Nautiyal", R.raw.lut_gye))
        list.add(SongData("Paani Paani", "Badshah", R.raw.pani_pani))
        list.add(SongData("Shor Machega", "Yo Yo Honey Singh", R.raw.shor_machega))
        list.add(SongData("Tu Aake Dekh", "King", R.raw.tu_aake_dekh))
        list.add(SongData("Coca Cola Tu", "Tony Kakkar, Neha Kakkar", R.raw.coca_cola_tu))
        list.add(SongData("Dekhte Dekhte", "Atif Aslam", R.raw.dekhte_dekhte))
        list.add(SongData("Jab Koi Baat", "Atif Aslam", R.raw.jab_koi_baat))
        list.add(SongData("Nazar Lg Jayegi", "Milind Gaba, Kamal Raja", R.raw.nazar_lg_jaegi))
        list.add(SongData("Pehla Nasha", "Udit Narayan, Sadhna Sargam", R.raw.pehla_nasha))
        list.add(SongData("Har Ek Friend Kamina Hota Hai", "Sajid-Wajid, Sonu Nigam", R.raw.har_ek_friend_kamina_hota_hai))
        list.add(SongData("Sun Saathiya", "Priya Saraiya, Divya Kumar", R.raw.sun_saathiya))
        list.add(SongData("Aaja Meri Bike Par", "Tony Kakkar", R.raw.aaja_meri_bike_pr))
        list.add(SongData("Tu He Yaar Mera", "Rochak Kohli", R.raw.tu_he_yaar_mera))
        list.add(SongData("Tujhe Kitna Chahne Lage Hum", "Arjit Singh", R.raw.tujhe_kitna_chahne_lge))
    }

}