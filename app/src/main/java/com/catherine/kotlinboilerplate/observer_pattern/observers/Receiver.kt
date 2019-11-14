package com.catherine.kotlinboilerplate.observer_pattern.observers

interface Receiver {
    fun onReceive(newState: String)
}