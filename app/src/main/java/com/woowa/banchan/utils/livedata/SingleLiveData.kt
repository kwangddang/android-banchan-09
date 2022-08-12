package com.woowa.banchan.utils.livedata

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData


class SingleLiveData<T> {

    private val liveData = MutableLiveData<SingleLiveEvent<T>>()

    constructor(value: T) {
        liveData.value = SingleLiveEvent(value)
    }

    fun setValue(value: T) {
        liveData.value = SingleLiveEvent(value)
    }

    fun postValue(value: T) {
        liveData.postValue(SingleLiveEvent(value))
    }

    fun getValue() = liveData.value?.peekContent()

    fun observe(owner: LifecycleOwner, onResult: (T) -> Unit) {
        liveData.observe(owner) { it.getContentIfNotHandled()?.let(onResult) }
    }

    fun observePeek(owner: LifecycleOwner, onResult: (T) -> Unit) {
        liveData.observe(owner) { onResult(it.peekContent()) }
    }

}