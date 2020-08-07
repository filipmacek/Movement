package com.filipmacek.movement.adapters

import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.filipmacek.movement.R
import com.filipmacek.movement.data.routes.Route
import com.filipmacek.movement.data.routes.RouteRepository
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.data.users.UserRepository
import org.koin.core.KoinComponent
import org.koin.core.inject


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
        holder.routeId.text=routes[position].routeId
        holder.maker.text=routes[position].maker
        holder.startLocation.text=routes[position].startLocation
        holder.endLocation.text=routes[position].endLocation

        holder.acceptButton.setOnClickListener {
            Toast.makeText(context,"Go",Toast.LENGTH_SHORT)
        }
        holder.infoButton.setOnClickListener {
            Toast.makeText(context,"Info",Toast.LENGTH_SHORT)
        }




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