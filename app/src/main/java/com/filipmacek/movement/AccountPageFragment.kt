package com.filipmacek.movement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.filipmacek.movement.databinding.AccountPageFragmentBinding
import com.filipmacek.movement.viewmodels.AccountViewModel
import org.koin.android.ext.android.inject


class AccountPageFragment :Fragment(){

    private lateinit var binding:AccountPageFragmentBinding

    private val viewModel:AccountViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AccountPageFragmentBinding.inflate(inflater,container,false)
        return binding.root


    }
}