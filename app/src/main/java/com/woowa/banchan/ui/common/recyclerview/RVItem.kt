package com.woowa.banchan.ui.common.recyclerview

sealed class RVItem {
    abstract val id: Int

    object Header : RVItem() {
        override val id = 0
    }

    object SubHeader : RVItem() {
        override val id = 1
    }

    object Footer : RVItem() {
        override val id = 2
    }

    data class Item<T>(val item: T) : RVItem() { // 아이템 항목
        override val id = 3
    }

    object Empty : RVItem() {
        override val id = Integer.MAX_VALUE
    }

}