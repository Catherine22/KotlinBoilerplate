package com.catherine.kotlinboilerplate.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.catherine.kotlinboilerplate.R
import com.catherine.kotlinboilerplate.data.Album
import com.catherine.kotlinboilerplate.delegation.OnAlbumClickListener
import com.facebook.drawee.view.SimpleDraweeView


class AlbumAdapter() : PagedListAdapter<Album, AlbumAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var mOnClickListener: OnAlbumClickListener? = null

    constructor(mOnClickListener: OnAlbumClickListener) : this() {
        this.mOnClickListener = mOnClickListener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
        MyViewHolder(parent, mOnClickListener)

    class MyViewHolder(parent: ViewGroup, private val mOnClickListener: OnAlbumClickListener?) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        ) {
        var album: Album? = null

        private val artist = itemView.findViewById<TextView>(R.id.tv_artist)
        private val title = itemView.findViewById<TextView>(R.id.tv_album)
        private val cover = itemView.findViewById<SimpleDraweeView>(R.id.sdv_album_cover)

        /**
         * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
         * ViewHolder when Item is loaded.
         */
        fun bindTo(album: Album?) {
            this.album = album

            clear()
            itemView.setOnClickListener {
                mOnClickListener?.onClick(it, album)
            }
            artist.text = album?.artist
            title.text = album?.title
            cover.setImageURI(album?.image)
        }

        private fun clear() {
            artist.text = ""
            title.text = ""
            cover.setImageURI("")
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
                return oldItem.compareTo(newItem) == 0
            }

            override fun areContentsTheSame(
                oldItem: Album,
                newItem: Album
            ): Boolean {
                return oldItem.title == newItem.title
            }
        }
    }
}