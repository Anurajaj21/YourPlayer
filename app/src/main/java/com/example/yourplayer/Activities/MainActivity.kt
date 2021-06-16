package com.example.yourplayer.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.yourplayer.Fragments.PlaylistFragment
import com.example.yourplayer.R
import com.example.yourplayer.ViewModels.PlaylistViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: PlaylistViewModel
    val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(PlaylistViewModel::class.java)

        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, PlaylistFragment())
        fragmentTransition.commit()

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