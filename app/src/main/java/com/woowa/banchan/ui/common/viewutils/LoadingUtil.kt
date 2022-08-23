package com.woowa.banchan.ui.common.viewutils

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.isVisible

fun showLoading(contentView: View, loadingView: ProgressBar) {
    contentView.isGone = true
    loadingView.isVisible = true
}

fun showContent(contentView: View, loadingView: ProgressBar) {
    contentView.isVisible = true
    loadingView.isGone = true
}