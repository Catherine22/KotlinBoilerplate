package com.catherine.kotlinboilerplate.observer_pattern.observers

import com.catherine.kotlinboilerplate.observer_pattern.Subject
import com.catherine.kotlinboilerplate.utilities.CLog

class BinaryObserver(override val subject: Subject) : Observer() {
    init {
        subject.attach(this)
    }

    override fun update() {
        if (subject.state == null)
            return
        val num = Integer.toBinaryString(subject.state!!)
        val newState = "toBinary: $num"
        CLog.d(HexObserver::class.java.simpleName, newState)
        notify(newState)
    }

}