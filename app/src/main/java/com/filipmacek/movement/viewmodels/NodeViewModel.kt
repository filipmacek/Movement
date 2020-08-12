package com.filipmacek.movement.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.nodes.NodeRepository

class NodeViewModel(private val nodeRepository: NodeRepository):ViewModel() {
    val nodes:LiveData<List<Node>> =  nodeRepository.getNodes()
}