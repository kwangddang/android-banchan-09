package com.woowa.banchan.ui.home.main

import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.woowa.banchan.ui.common.event.SingleEvent
import com.woowa.banchan.ui.common.event.setEvent
import com.woowa.banchan.ui.home.HomeBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : HomeBaseViewModel() {

    var type: String? = null

    private val _linearGridClickEvent = MutableLiveData<SingleEvent<Int>>()
    val linearGridClickEvent: LiveData<SingleEvent<Int>> get() = _linearGridClickEvent

    val checkedChangeListener: (RadioGroup, Int) -> Unit = { group, checkedId ->
        _linearGridClickEvent.setEvent(checkedId)
    }

    fun refreshFoodList() {
        super.getFoods(type!!)
    }
}