package com.filipmacek.movement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.filipmacek.movement.adapters.ACCOUNT_PAGE
import com.filipmacek.movement.adapters.DashboardPagerAdapter
import com.filipmacek.movement.adapters.NODES_PAGE
import com.filipmacek.movement.adapters.ROUTES_PAGE
import com.filipmacek.movement.databinding.DashboardFragmentBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import java.lang.IndexOutOfBoundsException

class DashboardFragment: Fragment(){
    private lateinit var binding: DashboardFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DashboardFragmentBinding.inflate(inflater,container,false)

        val tabLayout = binding.tabs
        val viewPager= binding.viewPager

        viewPager.adapter = DashboardPagerAdapter(this)

        TabLayoutMediator(tabLayout,viewPager) { tab,position ->
            tab.setIcon(getTabIcon(position))
            tab.text = getTabText(position)

        }.attach()

//        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)


        return binding.root;


    }

    private fun getTabIcon(position: Int):Int{
        return when(position) {
            ROUTES_PAGE -> R.drawable.routes_icon
            NODES_PAGE -> R.drawable.nodes_icon
            ACCOUNT_PAGE -> R.drawable.account_icon
            else -> throw IndexOutOfBoundsException()
        }
    }

    private fun getTabText(position: Int):String? {
        return when(position) {
            ROUTES_PAGE -> "Routes"
            NODES_PAGE ->  "Nodes"
            ACCOUNT_PAGE -> "Account"
            else -> null
        }
    }

}