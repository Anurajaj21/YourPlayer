package com.example.yourplayer.ViewModels

import android.app.Application
import android.media.MediaPlayer
import android.util.Log
import android.widget.SeekBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.yourplayer.Models.SongData
import com.example.yourplayer.R

class PlaylistViewModel(application: Application) : AndroidViewModel(application) {


    var mp  = MediaPlayer()

    val list = ArrayList<SongData>()

    private val _currentSong = MutableLiveData<SongData>()
    val currentSong : LiveData<SongData>
    get() = _currentSong

    private val _currentIndex = MutableLiveData<Int>()
    val currentIndex : LiveData<Int>
    get() = _currentIndex

    private val _play = MutableLiveData<Boolean>()
    val play : LiveData<Boolean>
    get() = _play

    private val _duration = MutableLiveData<Int>()
    val duration : LiveData<Int>
    get() = _duration

    fun playSong(song: SongData){

            mp.stop()
            mp.release()
            mp = MediaPlayer.create(getApplication(), song.source)

            mp.setOnPreparedListener {
                mp.start()
            }
            _duration.value = mp.duration

        _play.value = true
    }


    fun pauseSong() {
        mp.pause()
        _play.value = false
    }

    fun handlePlayPause(){

        if (mp.isPlaying){
            mp.pause()
            _play.value = false
        }else{
            Log.d("abcde", mp.toString())
            mp.start()

            _play.value = true
//            }
        }
    }

    fun resumeSong(){
        mp.start()
        _play.value = true
    }

    fun nextSong(){
        if (_currentIndex.value!! < list.size-1) {
            _currentIndex.value = _currentIndex.value?.plus(1)
        }else{
            _currentIndex.value = 0
        }
        _currentSong.value = list[_currentIndex.value!!]
        mp.pause()
        mp = MediaPlayer.create(getApplication(), _currentSong.value?.source!!)
        mp.start()
        _play.value = true
    }

    fun previousSong(){
        if (_currentIndex.value!! > 0) {
            _currentIndex.value = _currentIndex.value?.minus(1)
        }else{
            _currentIndex.value = list.size-1
        }
        _currentSong.value = list[_currentIndex.value!!]
        mp.pause()
        mp = MediaPlayer.create(getApplication(), _currentSong.value?.source!!)
        mp.start()
        _play.value = true
    }

    fun setCurrentSong(s : SongData){
        _currentSong.value = s
    }

    fun setCurrentIndex(i : Int){
        _currentIndex.value = i
    }


    fun setList(){
        list.clear()
        list.add(SongData("Har Ek Friend Kamina Hota Hai", "Unknown", R.raw.har_ek_friend_kamina_hota_hai))
        list.add(SongData("Hume Tumse Pyaar Kitna", "Unknown", R.raw.hume_tumse_pyaar_kitna))
        list.add(SongData("Believer", "Unknown", R.raw.believer))
        list.add(SongData("Brown Munde", "Unknown", R.raw.brown_munde))
        list.add(SongData("Casanova", "Unknown", R.raw.casanova))
        list.add(SongData("Hall of Fame", "Unknown", R.raw.hall_of_fame))
        list.add(SongData("Lut Gye", "Unknown", R.raw.lut_gye))
        list.add(SongData("Paani Paani", "Unknown", R.raw.pani_pani))
        list.add(SongData("Shor Machega", "Unknown", R.raw.shor_machega))
        list.add(SongData("Tu Aake Dekh", "Unknown", R.raw.tu_aake_dekh))
    }


    fun setInitially(){
        if (_currentSong.value == null){
            _currentSong.value = list[0]
            _currentIndex.value = 0
            mp = MediaPlayer.create(getApplication(), list[0].source)
            _duration.value = mp.duration
//            mp.start()
            _play.value = false
        }
    }

}