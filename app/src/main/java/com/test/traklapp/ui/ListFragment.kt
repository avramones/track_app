package com.test.traklapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.traklapp.R
import com.test.traklapp.adapter.TrackAdapter
import com.test.traklapp.model.Track
import com.test.traklapp.utils.AppConstants
import com.test.traklapp.vm.ListViewModel
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {

    lateinit var viewModel : ListViewModel
    var trackList  = ArrayList<Track>()
    lateinit var trackAdapter : TrackAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application).create(
            ListViewModel::class.java)

        trackAdapter = TrackAdapter(trackList)
        prepareRecyclerView()
        prepareToWork()
        search_view_track.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
               viewModel.fetchTracks(p0.toString(), AppConstants.ENTITY_TYPE_MUSIC_TRACK)
            }


        })

    }

    fun prepareRecyclerView() {
       recycler_view_track.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
       recycler_view_track.adapter = trackAdapter
        trackAdapter.setonItemClickListener(object : TrackAdapter.ItemClickListener {
            override fun onItemClick(view: View?, position: Int) {
                var bundle = Bundle()
                bundle.putSerializable("track", trackList[position])
                (activity as MainActivity).navController.navigate(R.id.action_listFragment_to_detailFragment,bundle)
            }

        })
    }

    fun prepareToWork() {
        activity?.let {
            viewModel.liveData.observe(
                it,
                Observer { tracks -> trackAdapter.addTracks(tracks) } )
        }
    }




}