package com.woowa.banchan.ui.common.event

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

open class SingleEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    fun peekContent(): T = content
}

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) :
    Observer<SingleEvent<T>> {
    override fun onChanged(event: SingleEvent<T>?) {
        event?.getContentIfNotHandled()?.let { value ->
            onEventUnhandledContent(value)
        }
    }
}

fun MutableLiveData<SingleEvent<Unit>>.setEvent() {
    value = SingleEvent(Unit)
}

fun <T> MutableLiveData<SingleEvent<T>>.setEvent(data: T) {
    value = SingleEvent(data)
}