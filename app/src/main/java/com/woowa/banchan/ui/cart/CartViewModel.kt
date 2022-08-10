package com.woowa.banchan.ui.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor() : ViewModel() {
    val fragmentTag = MutableLiveData<String>()

    fun setFragmentTag(tag: String) {
        fragmentTag.value = tag
    }
}