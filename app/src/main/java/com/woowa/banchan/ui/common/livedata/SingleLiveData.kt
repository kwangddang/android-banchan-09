package com.woowa.banchan.ui.common.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.woowa.banchan.ui.common.event.SingleEvent


class SingleLiveData<T> {

    private val liveData = MutableLiveData<SingleEvent<T>>()

    constructor(value: T) {
        liveData.value = SingleEvent(value)
    }

    fun setValue(value: T) {
        liveData.value = SingleEvent(value)
    }

    fun postValue(value: T) {
        liveData.postValue(SingleEvent(value))
    }

    fun getValue() = liveData.value?.peekContent()

    fun observe(owner: LifecycleOwner, onResult: (T) -> Unit) {
        liveData.observe(owner) { it.getContentIfNotHandled()?.let(onResult) }
    }

    fun observePeek(owner: LifecycleOwner, onResult: (T) -> Unit) {
        liveData.observe(owner) { onResult(it.peekContent()) }
    }

}