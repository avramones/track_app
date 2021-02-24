package com.test.traklapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.traklapp.R
import com.test.traklapp.model.Track
import java.lang.Math.round
import kotlin.math.roundToInt

class TrackAdapter(  private val trackList: ArrayList<Track>) :
    RecyclerView.Adapter<TrackAdapter.ViewHolder>() {
    private var mClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_track, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.title.text = trackList[position].trackName
        holder.author.text = trackList[position].artistName
        holder.time.text = (trackList[position].trackTimeMillis?.div(60000.0)?.roundTo(2))!!.replace(',',':')
        Picasso.with(holder.picture.context).load(trackList[position].artworkUrl100).fit().centerCrop().into(holder.picture)
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun setonItemClickListener(itemClickListener: ItemClickListener?) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    fun getItem(id: Int): Track {
        return trackList[id]
    }

    fun addTracks(tracks: List<Track>?) {
        trackList.clear();
        if (tracks != null) {
            trackList.addAll(tracks)
            notifyDataSetChanged()
        };

    }


    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var title: TextView = itemView.findViewById(R.id.title)
        var author: TextView = itemView.findViewById(R.id.author)
        var time: TextView = itemView.findViewById(R.id.time)
        var picture : ImageView = itemView.findViewById(R.id.picture_image)
        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, adapterPosition)
        }

        init {
            itemView.setOnClickListener(this)
        }
    }

    fun Double.roundTo(n : Int) : String {
        return "%.${n}f".format(this)
    }


}

