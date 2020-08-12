package com.filipmacek.movement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipmacek.movement.databinding.NodePageFragmentBinding
import com.filipmacek.movement.viewmodels.NodeViewModel
import org.koin.android.ext.android.inject
import com.filipmacek.movement.adapters.NodeListAdapter

class NodePageFragment :Fragment(){

    private lateinit var binding: NodePageFragmentBinding

    private val viewModel:NodeViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = NodePageFragmentBinding.inflate(inflater,container,false)

        viewModel.nodes.observe(viewLifecycleOwner, Observer { nodes ->
            val adapter = NodeListAdapter(nodes)
            binding.nodeList.adapter = adapter
            binding.nodeList.layoutManager = LinearLayoutManager(context)
        })



        return binding.root


    }
}