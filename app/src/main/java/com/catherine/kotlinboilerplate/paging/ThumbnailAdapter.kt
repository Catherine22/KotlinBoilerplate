package com.catherine.kotlinboilerplate.paging


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.catherine.kotlinboilerplate.R
import com.catherine.kotlinboilerplate.data.Album
import com.catherine.kotlinboilerplate.delegation.OnAlbumClickListener
import com.catherine.kotlinboilerplate.utilities.DeviceInfo
import com.facebook.drawee.view.SimpleDraweeView


class ThumbnailAdapter() : PagedListAdapter<Album, ThumbnailAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var onAlbumClickListener: OnAlbumClickListener? = null
    var cellWidth: Int? = null

    constructor(onAlbumClickListener: OnAlbumClickListener) : this() {
        this.onAlbumClickListener = onAlbumClickListener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        cellWidth =
            ((DeviceInfo.SCREEN_WIDTH * 1.0f) / NUMBER_OF_COLUMNS).toInt()
        return MyViewHolder(parent, onAlbumClickListener)
    }

    inner class MyViewHolder(
        parent: ViewGroup,
        private val onAlbumClickListener: OnAlbumClickListener?
    ) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_thumbnail, parent, false)
        ) {
        var album: Album? = null
        private val cover = itemView.findViewById<SimpleDraweeView>(R.id.sdv_album_cover)

        /**
         * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
         * ViewHolder when Item is loaded.
         */
        fun bindTo(album: Album?) {
            this.album = album

            clear()
            itemView.setOnClickListener {
                onAlbumClickListener?.onClick(it, album)
            }

            // reset the size of simpleDraweeView
            if (cellWidth != null) {
                val params = cover.layoutParams as RecyclerView.LayoutParams
                params.width = cellWidth!!
                params.height = cellWidth!!
                cover.layoutParams = params
            }
            cover.setImageURI(album?.image)
        }

        private fun clear() {
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

        const val NUMBER_OF_COLUMNS = 3
    }
}