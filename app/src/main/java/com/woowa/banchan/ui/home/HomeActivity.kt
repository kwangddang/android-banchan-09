package com.woowa.banchan.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.woowa.banchan.R
import com.woowa.banchan.databinding.ActivityHomeBinding
import com.woowa.banchan.ui.cart.CartActivity
import com.woowa.banchan.ui.home.adapter.HomeVPAdapter
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
        initButtonSetting()
    }

    private fun initAdapter() {
        binding.vpHome.adapter = HomeVPAdapter(supportFragmentManager, lifecycle)
    }

    private fun initTabLayoutMediator() {
        val tabList = listOf("기획전", "든든한 메인요리", "뜨끈한 국물요리", "정갈한 밑반찬")

        TabLayoutMediator(binding.layoutTab, binding.vpHome) { tab, position ->
            tab.text = tabList[position]
        }.attach()
    }

    private fun initButtonSetting() {
        binding.tbHome.setOnClickCartIcon {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }
}