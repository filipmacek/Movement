package com.filipmacek.movement.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.filipmacek.movement.R
import com.filipmacek.movement.api.Ready
import com.filipmacek.movement.data.nodes.Node
import com.filipmacek.movement.data.nodes.NodeRepository
import com.filipmacek.movement.databinding.NodePageFragmentBinding
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.node_row.view.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import java.util.concurrent.TimeUnit
import retrofit2.Callback
import retrofit2.Response


class NodeListAdapter(private var nodes: List<Node>):ListAdapter<Node,NodeListAdapter.ViewHolder>(NodeItemDiffCallback()),KoinComponent {

    private lateinit var context:Context

    private val nodeRepository:NodeRepository by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NodeListAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.node_row,parent,false)
        context = parent.context
        return ViewHolder(view)
    }

    @SuppressLint("CheckResult")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nodeId.text = nodes[position].nodeId
        holder.nodeName.text = nodes[position].nodeName
        holder.ip.text = nodes[position].ip

        Observable.interval(3000L,TimeUnit.MILLISECONDS)
            .timeInterval()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe {
                val response = nodeRepository.checkIfNodeReady(nodes[position].ip)
                response.enqueue(object: Callback<Ready>{
                    override fun onResponse(call: Call<Ready>, response: Response<Ready>) {
                        println("OnResponse")
                        if(response.body()?.status == "Ready"){
                            holder.nodeStatus.setBackgroundColor(Color.GREEN)
                            holder.nodeStatusText.text = "Connected"
                            holder.nodeStatusText.setTextColor(Color.BLACK)
                        }else {
                            holder.nodeStatus.setBackgroundColor(Color.RED)
                            holder.nodeStatusText.text = "Not Connected"
                            holder.nodeStatusText.setTextColor(Color.RED)
                        }
                    }

                    override fun onFailure(call: Call<Ready>, t: Throwable) {
                        println("OnFailure")
                        holder.nodeStatus.setBackgroundColor(Color.RED)
                        holder.nodeStatusText.text = "Not Connected"
                        holder.nodeStatusText.setTextColor(Color.RED)

                    }

                })
            }


    }

    override fun getItemCount() = nodes.size

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val nodeId:TextView = itemView.findViewById(R.id.nodeId)
        val nodeName:TextView = itemView.findViewById(R.id.nodeName)
        val ip:TextView = itemView.findViewById(R.id.nodeIp)
        val nodeStatus:ImageView = itemView.findViewById(R.id.nodeStatus)
        val nodeStatusText :TextView = itemView.findViewById(R.id.nodeStatusText)
    }




}

private class NodeItemDiffCallback: DiffUtil.ItemCallback<Node>(){
    override fun areItemsTheSame(oldItem: Node, newItem: Node): Boolean {
        return oldItem.nodeId ==  newItem.nodeId
    }

    override fun areContentsTheSame(oldItem: Node, newItem: Node): Boolean {
        return oldItem == newItem
    }

}