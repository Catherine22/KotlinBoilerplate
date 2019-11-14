package com.catherine.kotlinboilerplate

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.catherine.kotlinboilerplate.observer_pattern.Subject
import com.catherine.kotlinboilerplate.observer_pattern.observers.*
import kotlinx.android.synthetic.main.activity_observer_pattern.*
import kotlinx.android.synthetic.main.content_observer_pattern.*

class ObserverPatternActivity : AppCompatActivity() {
    private var binaryObserver: Observer? = null
    private var octalObserver: Observer? = null
    private var hexOctalObserver: Observer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_observer_pattern)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initView()
    }

    private fun initView() {
        val subject = Subject()
        binaryObserver = BinaryObserver(subject)
        binaryObserver?.register(object : Receiver {
            override fun onReceive(newState: String) {
                tv_binary.text = newState
            }
        })
        octalObserver = OctalObserver(subject)
        octalObserver?.register(object : Receiver {
            override fun onReceive(newState: String) {
                tv_octal.text = newState
            }
        })
        hexOctalObserver = HexObserver(subject)
        hexOctalObserver?.register(object : Receiver {
            override fun onReceive(newState: String) {
                tv_hex.text = newState
            }
        })
        et_input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(et_input.text))
                    subject.state = Integer.parseInt(et_input.text.toString())
                else
                    subject.state = 0
            }

        })
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

    override fun onDestroy() {
        binaryObserver?.unregisterAll()
        octalObserver?.unregisterAll()
        hexOctalObserver?.unregisterAll()
        super.onDestroy()
    }
}