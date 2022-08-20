package com.woowa.banchan.ui.common.event

import androidx.lifecycle.MutableLiveData

open class SingleEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}

fun MutableLiveData<SingleEvent<Unit>>.emit() {
    value = SingleEvent(Unit)
}

fun<T> MutableLiveData<SingleEvent<T>>.emit(data: T) {
    value = SingleEvent(data)
}