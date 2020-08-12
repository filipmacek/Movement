package com.filipmacek.movement.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.filipmacek.movement.AccountPageFragment
import com.filipmacek.movement.NodePageFragment
import com.filipmacek.movement.RoutePageFragment
import java.lang.IndexOutOfBoundsException

const val ROUTES_PAGE= 0
const val NODES_PAGE = 1
const val ACCOUNT_PAGE = 2


class DashboardPagerAdapter (fragment:Fragment):FragmentStateAdapter(fragment) {

    // Mapping of the ViewPager pages
    private val tabFragments: Map<Int,()-> Fragment> = mapOf(
        ROUTES_PAGE to { RoutePageFragment() },
        NODES_PAGE to { NodePageFragment() },
        ACCOUNT_PAGE to { AccountPageFragment() }

    )

    override fun getItemCount(): Int = tabFragments.size

    override fun createFragment(position: Int): Fragment {
        return tabFragments[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}