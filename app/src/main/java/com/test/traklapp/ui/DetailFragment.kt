package com.test.traklapp.ui

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.test.traklapp.R
import com.test.traklapp.model.Track
import com.test.traklapp.vm.DetailViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_detail.*
import java.io.Serializable

class DetailFragment : Fragment() {

    lateinit var viewModel: DetailViewModel
    lateinit var track: Track
    lateinit var mediaPlayer: MediaPlayer
    lateinit var changeObservable : PublishSubject<Boolean>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        play_button.setOnClickListener { play() }
        changeObservable = PublishSubject.create()
        changeObservable.subscribe { state -> stateButton(state)  }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(DetailViewModel::class.java)
        track = arguments?.getSerializable("track") as Track
        Picasso.with(activity).load(track.artworkUrl100).into(poster)
        initPlayer()
    }

    fun play() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            changeObservable.onNext(true)
        } else {
            mediaPlayer.pause()
            changeObservable.onNext(false)
        }
    }

    fun stateButton(state : Boolean) {
        if (state)
            play_button.setImageDrawable(activity?.let { ContextCompat.getDrawable(it, R.drawable.ic_pause) })
        else
            play_button.setImageDrawable(activity?.let { ContextCompat.getDrawable(it, R.drawable.ic_play) })
    }

    fun initPlayer() {
        mediaPlayer = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
        }
        mediaPlayer.setDataSource(track.previewUrl)
        mediaPlayer.prepare()
        mediaPlayer.setOnCompletionListener { changeObservable.onNext(false) }
    }


}


