package com.filipmacek.movement.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filipmacek.movement.MovementFragment
import com.filipmacek.movement.R
import com.filipmacek.movement.data.routes.Route
import com.google.android.material.dialog.MaterialAlertDialogBuilder


class RouteListAdapter (private var routes: List<Route>): ListAdapter<Route, RouteListAdapter.ViewHolder>(RouteItemDiffCallback()) {

    // Context global var
    private lateinit var context: Context

//    private val userRepository: RouteRepository by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.route_row,parent,false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.routeId.text=makeProperString(routes[position].routeId)
        holder.maker.text=makeProperString(routes[position].maker)
        holder.startLocation.text=routes[position].startLocation
        holder.endLocation.text=routes[position].endLocation

        val dialog=MaterialAlertDialogBuilder(context)
            .setTitle("You are starting route.Proceed?")
            .setNegativeButton("No") { dialog,which ->
                println("Exiting route")

            }
            .setPositiveButton("Yes") { dialog,which ->
                Log.i("Route starts","Route is starting")
                val bundle_start = bundleOf("routeId" to routes[position].routeId)
                Navigation.findNavController(holder.itemView).navigate(R.id.action_dashboard_to_movement_page,bundle_start)
            }

        holder.acceptButton.setOnClickListener {
            dialog.show()
        }
        val bundle_info = bundleOf("startLocation" to routes[position].startLocation,"endLocation" to routes[position].endLocation)
        holder.infoButton.setOnClickListener {
            Navigation.findNavController(holder.itemView).navigate(R.id.action_dashboard_to_route_info,bundle_info)
        }




    }

    private fun makeProperString(temp:String):String{
        return temp.substring(0,12)
    }

    override fun getItemCount() = routes.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val routeId: TextView = itemView.findViewById(R.id.routeId)
        val maker: TextView = itemView.findViewById(R.id.maker)
        val startLocation: TextView = itemView.findViewById(R.id.startLocation)
        val endLocation: TextView = itemView.findViewById(R.id.endLocation)
        val acceptButton: Button = itemView.findViewById(R.id.accept_button)
        val infoButton: Button = itemView.findViewById(R.id.info_button)
    }

}

private class RouteItemDiffCallback: DiffUtil.ItemCallback<Route>() {
    override fun areContentsTheSame(oldItem: Route, newItem: Route): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: Route, newItem: Route): Boolean {
        return oldItem.routeId== newItem.routeId
    }
}