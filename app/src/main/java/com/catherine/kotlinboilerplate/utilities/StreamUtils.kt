package com.catherine.kotlinboilerplate.utilities

inline fun <reified T> toArray(list: List<*>): Array<T> {
    return (list as List<T>).toTypedArray()
}