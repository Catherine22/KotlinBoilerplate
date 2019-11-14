package com.catherine.kotlinboilerplate.observer_pattern

import com.catherine.kotlinboilerplate.observer_pattern.observers.Observer

class Subject {
    private var observers = arrayListOf<Observer>()
    var state: Int? = null
        set(value) {
            field = value
            notifyAllObservers()
        }

    fun attach(observer: Observer) {
        observers.add(observer)
    }

    private fun notifyAllObservers() {
        observers.forEach {
            it.update()
        }
    }
}