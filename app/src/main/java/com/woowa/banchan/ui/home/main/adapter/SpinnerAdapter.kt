package com.woowa.banchan.ui.home.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckedTextView
import android.widget.TextView
import com.woowa.banchan.R

class SpinnerAdapter(context: Context) : BaseAdapter(){

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val list = listOf("기본 정렬순", "금액 높은순", "금액 낮은순", "할인율순")

    override fun getCount(): Int = list.size

    override fun getItem(position: Int): Any = list[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if(convertView == null) {
            val view = inflater.inflate(R.layout.spinner_title, parent, false)
            view.findViewById<TextView>(R.id.text_spinner_title).text = list[position]
            return view
        }
        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if(convertView == null) {
            val view = inflater.inflate(R.layout.spinner_content, parent, false)
            view.findViewById<CheckedTextView>(R.id.cb_spinner).text = list[position]
            return view
        }
        return convertView
    }
}