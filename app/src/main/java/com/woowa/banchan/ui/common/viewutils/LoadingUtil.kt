package com.woowa.banchan.ui.common.viewutils

import android.view.View
import android.widget.ProgressBar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.woowa.banchan.ui.common.custom.CustomErrorView

fun showLoading(contentView: View, loadingView: ProgressBar, errorView: CustomErrorView) {
    contentView.isGone = true
    loadingView.isVisible = true
    errorView.isGone = true
}

fun showContent(contentView: View, loadingView: ProgressBar, errorView: CustomErrorView) {
    contentView.isVisible = true
    loadingView.isGone = true
    errorView.isGone = true
}

fun showError(contentView: View, loadingView: ProgressBar, errorView: CustomErrorView) {
    contentView.isGone = true
    loadingView.isGone = true
    errorView.isVisible = true
}