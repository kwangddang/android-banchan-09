package com.woowa.banchan.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.woowa.banchan.R
import com.woowa.banchan.ui.common.bottomsheet.CartAddFragment
import com.woowa.banchan.ui.common.event.EventObserver
import com.woowa.banchan.ui.common.key.FOOD
import com.woowa.banchan.ui.common.key.FOOD_DETAIL_HASH
import com.woowa.banchan.ui.common.key.FOOD_DETAIL_TITLE
import com.woowa.banchan.ui.detail.DetailActivity
import com.woowa.banchan.ui.home.adapter.HomeRVAdapter

abstract class HomeBaseFragment<T : ViewDataBinding>(@LayoutRes val layoutRes: Int) : Fragment() {

    lateinit var binding: T

    abstract val viewModel: HomeBaseViewModel

    val homeRVAdapter: HomeRVAdapter by lazy {
        HomeRVAdapter(
            viewModel.itemClickListener,
            viewModel.cartClickListener
        ).apply { managerType = GRID }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutRes, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initBinding()
        initViews()
        initObserver()
        initClickObserver()
    }

    private fun initClickObserver() {
        viewModel.cartClickEvent.observe(viewLifecycleOwner, EventObserver { food ->
            if (food.checkState)
                viewModel.deleteCart(food.detailHash)
            else {
                val fragment = CartAddFragment()
                val bundle = Bundle()
                bundle.putSerializable(FOOD, food)
                fragment.arguments = bundle
                fragment.show(
                    childFragmentManager,
                    getString(R.string.fragment_cart_add)
                )
            }
        })

        viewModel.itemClickEvent.observe(viewLifecycleOwner, EventObserver {
            val intent = Intent(context, DetailActivity::class.java)
            val titleHash = it.split(",")

            intent.putExtra(FOOD_DETAIL_TITLE, titleHash[0])
            intent.putExtra(FOOD_DETAIL_HASH, titleHash[1])
            startActivity(intent)
        })
    }

    abstract fun initAdapter()
    abstract fun initViews()
    abstract fun initObserver()
    abstract fun initBinding()

}