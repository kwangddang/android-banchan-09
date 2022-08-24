package com.woowa.banchan.ui.home.soup

import com.woowa.banchan.ui.home.HomeBaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SoupViewModel @Inject constructor() : HomeBaseViewModel() {

    var type: String? = null

    fun refreshFoodList() {
        getFoods(type!!)
    }
}