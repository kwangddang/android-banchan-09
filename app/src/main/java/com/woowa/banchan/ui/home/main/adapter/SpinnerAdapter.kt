package com.woowa.banchan.ui.home.main.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import android.widget.TextView
import com.woowa.banchan.R

class SpinnerAdapter(context: Context) : BaseAdapter() {

    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val list = listOf("기본 정렬순", "금액 높은순", "금액 낮은순", "할인율순")

    var selectedPosition = 0

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): String = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_title, parent, false)
        view.findViewById<TextView>(R.id.tv_spinner).text = getItem(position)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_content, parent, false)
        setCheckedTextView(view,position)
        return view
    }

    private fun setCheckedTextView(view: View, position: Int) {
        val checkedTextView = view.findViewById<CheckedTextView>(R.id.ct_spinner)
        checkedTextView.text = getItem(position)
        if(position == selectedPosition) {
            checkedTextView.typeface = Typeface.DEFAULT_BOLD
        } else {
            checkedTextView.typeface = Typeface.DEFAULT
        }
    }
}