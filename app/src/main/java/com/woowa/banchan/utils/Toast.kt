package com.woowa.banchan.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String?) {
    Toast.makeText(context, message ?: "에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showToast(message: String?) {
    Toast.makeText(this, message ?: "에러가 발생하였습니다.", Toast.LENGTH_SHORT).show()
}