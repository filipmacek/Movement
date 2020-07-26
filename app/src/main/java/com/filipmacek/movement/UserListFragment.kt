package com.filipmacek.movement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.filipmacek.movement.adapters.UsersAdapter
import com.filipmacek.movement.databinding.UserListFragmentBinding
import kotlinx.android.synthetic.main.user_list_fragment.*

class UserListFragment :Fragment(){

    private lateinit var binding: UserListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserListFragmentBinding.inflate(inflater,container,false)




       binding.listUserToolbar.setNavigationOnClickListener { view->
           view.findNavController().navigateUp()
       }
        return binding.root






    }
}