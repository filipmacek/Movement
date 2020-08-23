package com.filipmacek.movement.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.filipmacek.movement.R
import com.filipmacek.movement.data.routes.Route
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.core.KoinComponent
import org.koin.core.inject


class RouteListAdapter (private var routes: List<Route>,private val username: String,private val navController: NavController): ListAdapter<Route, RouteListAdapter.ViewHolder>(RouteItemDiffCallback()),KoinComponent {


    // Context global var
    private lateinit var context: Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.route_row,parent,false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.routeId.text=routes[position].routeId
        holder.startLocation.text=routes[position].startLocation
        holder.endLocation.text=routes[position].endLocation
        holder.routeDescription.text = routes[position].description


        if(routes[position].isStarted == false && routes[position].isFinished == false){
            // Route is neither started nor finished then its Available
            holder.routeStatus.text = "Available"
            holder.routeStatus.setTextColor(Color.GREEN)
        }
        if(routes[position].isStarted == false && routes[position].isFinished == true) {

        }



        val dialog=MaterialAlertDialogBuilder(context)
            .setTitle("You are starting route.Proceed?")
            .setNegativeButton("No") { dialog,which ->
                println("Exiting route")

            }
            .setPositiveButton("Yes") { dialog,which ->
                Log.i("Route starts","Route is starting")
                val bundle_start = bundleOf("routeId" to routes[position].routeId,"username" to username)
                navController.navigate(R.id.action_dashboard_to_movement_page,bundle_start)

            }

        holder.acceptButton.setOnClickListener {
            dialog.show()
        }
        val bundle_info = bundleOf("startLocation" to routes[position].startLocation,"endLocation" to routes[position].endLocation)
        holder.infoButton.setOnClickListener {
            navController.navigate(R.id.action_dashboard_to_route_info,bundle_info)
        }





    }

    private fun makeProperString(temp:String):String{
        return temp.substring(0,12)
    }

    override fun getItemCount() = routes.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val routeId: TextView = itemView.findViewById(R.id.routeId)
        val startLocation: TextView = itemView.findViewById(R.id.startLocation)
        val endLocation: TextView = itemView.findViewById(R.id.endLocation)
        val routeStatus:TextView = itemView.findViewById(R.id.routeStatus)
        val acceptButton: Button = itemView.findViewById(R.id.accept_button)
        val infoButton: Button = itemView.findViewById(R.id.info_button)
        val routeDescription:TextView = itemView.findViewById(R.id.routeDescription)
    }


    private fun getLoc(s:String,t:Int):Double {
        if(t==0)  {
            return (s.substring(0,s.indexOf(",")).toDouble())
        } else {
            return (s.substring(s.indexOf(",")+1).toDouble())
        }

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