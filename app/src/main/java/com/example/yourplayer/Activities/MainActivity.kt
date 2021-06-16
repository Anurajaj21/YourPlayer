package com.example.yourplayer.Activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.yourplayer.Fragments.PlaylistFragment
import com.example.yourplayer.R
import com.example.yourplayer.ViewModels.PlaylistViewModel
import kotlin.properties.Delegates

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

    override fun onStart() {
        viewModel.pauseSong()
        super.onStart()
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