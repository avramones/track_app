package com.test.traklapp.utils

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.widget.Toast
import com.test.traklapp.api.DataManager
import com.test.traklapp.model.Track
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Toast.makeText(context, "Alarm! Wake up! Wake up!", Toast.LENGTH_LONG).show()

        fetchTracks(randomLetter(), AppConstants.ENTITY_TYPE_MUSIC_TRACK, context)
    }

    fun randomLetter(): String {
        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val rnd = Random()
        return alphabet[rnd.nextInt(alphabet.length)].toString()
    }

    fun initPlayer(track :Track, context: Context?) {
        val mediaPlayer: MediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepare()
        mediaPlayer.start()

    }

    @SuppressLint("CheckResult")
    fun fetchTracks(term: String?, entity: String?, context: Context?) {
        val dataManager = DataManager()
        dataManager.getSearchResults(term, entity)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe { response -> response?.results?.let { getRandomTrack(it,context) } };
    }

    fun getRandomTrack(tracks : List<Track>, context: Context?) {
        val rnd = Random()
      //  initPlayer(tracks[rnd.nextInt(tracks.size)],context)
        val startIntent = context
            ?.getPackageManager()
            ?.getLaunchIntentForPackage(context!!.packageName)

        startIntent!!.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                Intent.FLAG_ACTIVITY_NEW_TASK or
                Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
        startIntent.putExtra("track", tracks[rnd.nextInt(tracks.size)])
        context!!.startActivity(startIntent)
    }

}