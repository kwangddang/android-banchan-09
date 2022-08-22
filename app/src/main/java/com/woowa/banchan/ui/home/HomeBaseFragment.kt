package com.woowa.banchan.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.woowa.banchan.R
import com.woowa.banchan.domain.model.FoodItem
import com.woowa.banchan.ui.common.bottomsheet.CartAddFragment
import com.woowa.banchan.ui.detail.DetailActivity
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter

abstract class HomeBaseFragment<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment() {

    lateinit var binding: T

    abstract val viewModel: HomeBaseViewModel

    val itemClickListener: (String, String) -> Unit = { title, hash ->
        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("title", title)
        intent.putExtra("hash", hash)
        startActivity(intent)
    }

    val cartClickListener: (FoodItem) -> Unit = { food ->
        if (food.checkState)
            viewModel.deleteCart(food.detailHash)
        else
            CartAddFragment(food).show(childFragmentManager, getString(R.string.fragment_cart_add))
    }

    val homeRVAdapter: HomeRVAdapter by lazy {
        HomeRVAdapter(itemClickListener, cartClickListener).apply { managerType = GRID }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initViews()
        initObserve()
    }

    abstract fun initAdapter()
    abstract fun initViews()
    abstract fun initObserve()

}