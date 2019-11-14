package com.catherine.kotlinboilerplate.observer_pattern.observers

import com.catherine.kotlinboilerplate.observer_pattern.Subject

abstract class Observer {
    open val subject: Subject? = null
    abstract fun update()

    // callbacks
    private val receivers = arrayListOf<Receiver>()

    fun register(receiver: Receiver) {
        receivers.add(receiver)
    }

    fun unregister(receiver: Receiver) {
        receivers.remove(receiver)
    }

    fun unregisterAll() {
        receivers.clear()
    }

    fun notify(newState: String) {
        receivers.forEach {
            it.onReceive(newState)
        }
    }
}