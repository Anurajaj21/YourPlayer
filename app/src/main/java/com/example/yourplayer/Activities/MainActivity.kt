package com.example.yourplayer.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.yourplayer.Fragments.PlaylistFragment
import com.example.yourplayer.R

class MainActivity : AppCompatActivity() {

    val fragmentManager = supportFragmentManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container, PlaylistFragment())
        fragmentTransition.commit()

    }
}