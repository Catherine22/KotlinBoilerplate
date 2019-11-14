package com.catherine.kotlinboilerplate.paging


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.catherine.kotlinboilerplate.R
import com.catherine.kotlinboilerplate.data.Post
import com.catherine.kotlinboilerplate.delegation.OnPostClickListener
import com.facebook.drawee.view.SimpleDraweeView


class PostAdapter() : PagedListAdapter<Post, PostAdapter.MyViewHolder>(DIFF_CALLBACK) {

    private var mOnClickListener: OnPostClickListener? = null

    constructor(mOnClickListener: OnPostClickListener) : this() {
        this.mOnClickListener = mOnClickListener
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(parent, mOnClickListener)
    }

    inner class MyViewHolder(
        parent: ViewGroup,
        private val mOnClickListener: OnPostClickListener?
    ) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
        ) {
        var post: Post? = null
        private val cover = itemView.findViewById<SimpleDraweeView>(R.id.sdv_photo)
        private val title = itemView.findViewById<TextView>(R.id.tv_title)
        private val url = itemView.findViewById<TextView>(R.id.tv_url)


        /**
         * Items might be null if they are not paged in yet. PagedListAdapter will re-bind the
         * ViewHolder when Item is loaded.
         */
        @SuppressLint("SetTextI18n")
        fun bindTo(post: Post?) {
            this.post = post

            clear()
            itemView.setOnClickListener {
                mOnClickListener?.onClick(it, post)
            }

            cover.setImageURI(post?.thumbnailUrl)
            title.text = "[${post?.id}] ${post?.title}"
            url.text = post?.url
        }

        private fun clear() {
            cover.setImageURI("")
        }

    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
                return oldItem.compareTo(newItem) == 0
            }

            override fun areContentsTheSame(
                oldItem: Post,
                newItem: Post
            ): Boolean {
                return oldItem.albumId == newItem.albumId
            }
        }
    }
}