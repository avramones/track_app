package com.test.traklapp.ui

import android.annotation.SuppressLint
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.test.traklapp.R
import com.test.traklapp.model.Track
import com.test.traklapp.vm.DetailViewModel
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_detail.*

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
                .create(DetailViewModel::class.java)
        track = arguments?.getSerializable("track") as Track
        Picasso.with(activity).load(track.artworkUrl100?.replace("100", "600")).fit().centerCrop().into(poster)
        title.text = "%s - %s".format(track.artistName, track.trackName)
        changeObservable = PublishSubject.create()
        changeObservable.subscribe { state -> stateButton(state)  }
        (activity as MainActivity).bottomNavigationView.visibility = View.GONE
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

    private fun stateButton(state : Boolean) {
        if (state)
            play_button.setImageDrawable(activity?.let { ContextCompat.getDrawable(it, R.drawable.ic_pause) })
        else
            play_button.setImageDrawable(activity?.let { ContextCompat.getDrawable(it, R.drawable.ic_play) })
    }

    private fun initPlayer() {
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
        mediaPlayer.start()
        changeObservable.onNext(true)
        mediaPlayer.setOnCompletionListener { changeObservable.onNext(false) }
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).bottomNavigationView.visibility = View.VISIBLE
    }


}


