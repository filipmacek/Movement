package com.filipmacek.movement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipmacek.movement.adapters.RouteListAdapter
import com.filipmacek.movement.adapters.UserListAdapter
import com.filipmacek.movement.databinding.RoutePageFragmentBinding
import com.filipmacek.movement.viewmodels.RouteViewModel
import org.koin.android.ext.android.inject

class RoutePageFragment :Fragment(){

    private lateinit var binding: RoutePageFragmentBinding

    private val viewModel:RouteViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RoutePageFragmentBinding.inflate(inflater,container,false)


        viewModel.routes.observe(viewLifecycleOwner, Observer { routes ->

            val adapter = RouteListAdapter(routes)
            binding.routeList.adapter = adapter
            binding.routeList.layoutManager = LinearLayoutManager(context)
        })




        return binding.root


    }
}