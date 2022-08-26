package com.woowa.banchan.ui.common.popup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent

class CartCompleteViewModel : ViewModel() {

    private val _confirmClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val confirmClickEvent: LiveData<SingleEvent<Unit>> get() = _confirmClickEvent

    private val _continueClickEvent = MutableLiveData<SingleEvent<Unit>>()
    val continueClickEvent: LiveData<SingleEvent<Unit>> get() = _continueClickEvent

    fun setConfirmClickEvent() {
        _confirmClickEvent.setEvent()
    }

    fun setContinueClickEvent() {
        _continueClickEvent.setEvent()
    }
}