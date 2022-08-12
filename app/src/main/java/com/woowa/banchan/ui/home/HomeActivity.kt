package com.woowa.banchan.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityHomeBinding
import com.woowa.banchan.ui.cart.CartActivity
import com.woowa.banchan.ui.home.best.BestFragment
import com.woowa.banchan.ui.home.main.MainFragment
import com.woowa.banchan.ui.home.side.SideFragment
import com.woowa.banchan.ui.home.soup.SoupFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Banchan)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        initAdapter()
        initTabLayoutMediator()
    }

    private fun initAdapter() {
        binding.viewPager.adapter =
            object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
                override fun getItemCount(): Int = 4

                override fun createFragment(position: Int): Fragment =
                    when (position) {
                        0 -> BestFragment()
                        1 -> MainFragment()
                        2 -> SoupFragment()
                        3 -> SideFragment()
                        else -> Fragment()
                    }
            }
        startActivity(Intent(this, CartActivity::class.java))
    }

    private fun initTabLayoutMediator() {
        val tabList = listOf("기획전", "든든한 메인요리", "뜨끈한 국물요리", "정갈한 밑반찬")

        TabLayoutMediator(binding.layoutTab, binding.viewPager) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }
}