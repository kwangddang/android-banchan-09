package com.woowa.banchan.ui.home

// Home Activity Adapter Tag
const val HOME_HEADER = 1
const val SUB_HEADER = 2
const val HOME_ITEM = 3
const val LINEAR_VERTICAL = 4
const val LINEAR_HORIZONTAL = 5
const val GRID = 6

sealed class RecyclerViewItem {
    abstract val id: Int

    object Header : RecyclerViewItem() {
        override val id = 0
    }

    object SubHeader : RecyclerViewItem() {
        override val id = 1
    }

    data class Item<T>(val item: T) : RecyclerViewItem() { // 아이템 항목
        override val id = 2
    }

    object Empty : RecyclerViewItem() {
        override val id = Integer.MAX_VALUE
    }

}