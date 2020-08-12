package com.filipmacek.movement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipmacek.movement.adapters.UserListAdapter
import com.filipmacek.movement.blockchain.SmartContract
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.databinding.UserListFragmentBinding
import com.filipmacek.movement.viewmodels.UserViewModel
import org.koin.android.ext.android.inject


class UserListFragment :Fragment(){

    private lateinit var binding: UserListFragmentBinding

    private val viewModel:UserViewModel by inject()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserListFragmentBinding.inflate(inflater,container,false)


        viewModel.users.observe(viewLifecycleOwner, Observer { users ->

            val adapter = UserListAdapter(users)
            binding.userList.adapter = adapter
            binding.userList.layoutManager = LinearLayoutManager(context)
        })




       binding.listUserToolbar.setNavigationOnClickListener { view->
           view.findNavController().navigateUp()
       }
        return binding.root

    }




}