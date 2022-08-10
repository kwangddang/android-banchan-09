package com.woowa.banchan.ui.home.best

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.woowa.banchan.R
import com.woowa.banchan.databinding.FragmentBestBinding

class BestFragment : Fragment() {
    private lateinit var binding: FragmentBestBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_best, container, false)
        return binding.root
    }

}