package com.catherine.kotlinboilerplate.paging.ui

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.catherine.kotlinboilerplate.R

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val tabs = context.resources.getStringArray(R.array.boilerplate_tabs)

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> AlbumListFragment.newInstance(position)
        1 -> ThumbnailsFragment.newInstance(position)
        2 -> PostWallFragment.newInstance(position)
        else -> throw IndexOutOfBoundsException("You cannot lunch the fragment with the index out of ${count - 1}")
    }

    override fun getPageTitle(position: Int): String = tabs[position]

    override fun getCount() = tabs.size
}