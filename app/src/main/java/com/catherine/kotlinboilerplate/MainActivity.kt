package com.catherine.kotlinboilerplate

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.catherine.kotlinboilerplate.delegation.OnItemClickListener
import com.catherine.kotlinboilerplate.paging.*
import com.catherine.kotlinboilerplate.utilities.DeviceInfo
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), OnItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initView()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initView() {
        val arr = resources.getStringArray(R.array.main_features)
        val mAdapter = MainAdapter(arr, this@MainActivity)
        rv_features.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = mAdapter
        }

        // 16:9
        val height = (DeviceInfo.SCREEN_WIDTH * 1.0f * 9 / 16).roundToInt()
        val params =
            ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT, height)
        dashboard_area.layoutParams = params

        val mPostViewModelFactory = PostWallViewModelFactory().createFactory(this)
        val mPostViewModel = ViewModelProviders.of(this, mPostViewModelFactory)
            .get(PostWallViewModel::class.java)
        mPostViewModel.count?.observe(this, Observer<Int> {
            tv_posts_desc.text =
                String.format(resources.getString(R.string.dashboard_posts), it)
        })


        val mAlbumViewModelFactory = ShowAlbumViewModelFactory().createFactory(this)
        val mAlbumViewModel = ViewModelProviders.of(this, mAlbumViewModelFactory)
            .get(ShowAlbumViewModel::class.java)
        mAlbumViewModel.albumCount?.observe(this, Observer<Int> {
            tv_albums_desc.text =
                String.format(resources.getString(R.string.dashboard_albums), it)
        })
        mAlbumViewModel.artistCount?.observe(this, Observer<Int> {
            tv_artists_desc.text =
                String.format(resources.getString(R.string.dashboard_artists), it)
        })

    }


    override fun onClick(view: View, pos: Int) {
        val intent = Intent()
        when (pos) {
            0 -> {
                intent.setClass(this, ObserverPatternActivity::class.java)
                startActivity(intent)
            }

            1 -> {
                intent.setClass(this, BoilerplateActivity::class.java)
                startActivity(intent)
            }
        }
    }
}
