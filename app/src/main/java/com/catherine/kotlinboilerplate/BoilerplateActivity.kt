package com.catherine.kotlinboilerplate

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.catherine.kotlinboilerplate.paging.ui.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_boilerplate.*


class BoilerplateActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_boilerplate)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initView()
    }

    private fun initView() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        title = resources.getStringArray(R.array.main_features)[1]
        view_pager.adapter = sectionsPagerAdapter
        tabs.setupWithViewPager(view_pager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}