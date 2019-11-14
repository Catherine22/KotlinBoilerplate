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
import com.catherine.kotlinboilerplate.data.Album
import com.catherine.kotlinboilerplate.delegation.OnAlbumClickListener
import com.catherine.kotlinboilerplate.paging.AlbumAdapter
import com.catherine.kotlinboilerplate.paging.ShowAlbumViewModel
import com.catherine.kotlinboilerplate.paging.ShowAlbumViewModelFactory

class AlbumListFragment : Fragment(), OnAlbumClickListener {

    private lateinit var mViewModel: ShowAlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mViewModelFactory = ShowAlbumViewModelFactory().createFactory(activity!!)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
            .get(ShowAlbumViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_albums, container, false)
        val albumList = root.findViewById<RecyclerView>(R.id.rv_albums)
        val adapter = AlbumAdapter(this)
        albumList.apply {
            layoutManager = LinearLayoutManager(activity)
            this.adapter = adapter
        }
        mViewModel.loadAll().observe(viewLifecycleOwner, Observer<PagedList<Album>> {
            adapter.submitList(it)
        })
        return root
    }

    override fun onClick(view: View, album: Album?) {
        if (TextUtils.isEmpty(album?.url))
            return
        val customTabsIntent = CustomTabsIntent.Builder()
            .setToolbarColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            .build()
        customTabsIntent.intent.data = Uri.parse(album!!.url)
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
        fun newInstance(sectionNumber: Int): AlbumListFragment {
            return AlbumListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}