package com.woowa.banchan.ui.home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.woowa.banchan.ui.home.best.BestFragment
import com.woowa.banchan.ui.home.main.MainFragment
import com.woowa.banchan.ui.home.side.SideFragment
import com.woowa.banchan.ui.home.soup.SoupFragment

class HomeVPAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

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