package com.catherine.kotlinboilerplate.paging.ui

import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.catherine.kotlinboilerplate.R
import com.catherine.kotlinboilerplate.data.Post
import com.catherine.kotlinboilerplate.delegation.OnPostClickListener
import com.catherine.kotlinboilerplate.paging.PostAdapter
import com.catherine.kotlinboilerplate.paging.PostWallViewModel
import com.catherine.kotlinboilerplate.paging.PostWallViewModelFactory

class PostWallFragment : Fragment(), OnPostClickListener {

    private lateinit var mViewModel: PostWallViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mViewModelFactory = PostWallViewModelFactory().createFactory(activity!!)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
            .get(PostWallViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_post_wall, container, false)
        val albumList = root.findViewById<RecyclerView>(R.id.rv_posts)
        val adapter = PostAdapter(this)
        albumList.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapter
        }
        mViewModel.loadAll().observe(viewLifecycleOwner, Observer<PagedList<Post>> {
            adapter.submitList(it)
        })
        return root
    }

    override fun onClick(view: View, post: Post?) {
        if (TextUtils.isEmpty(post?.url))
            return
        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            .build()
        customTabsIntent.intent.data = Uri.parse(post!!.url)
        startActivity(customTabsIntent.intent)
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): PostWallFragment {
            return PostWallFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}