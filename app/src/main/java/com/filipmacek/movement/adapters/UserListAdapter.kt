package com.filipmacek.movement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

import androidx.recyclerview.widget.RecyclerView
import com.filipmacek.movement.R
import com.filipmacek.movement.data.users.User

class UsersAdapter (private var users: List<User>):RecyclerView.Adapter<UsersAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.user_row,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text=users[position].username
        holder.address.text=users[position].address

        holder.connect_button.setOnClickListener {
            holder.address.text="User wants to connect here"
        }



    }





    override fun getItemCount() = users.size


    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val address: TextView = itemView.findViewById(R.id.address)
        val connect_button: Button = itemView.findViewById(R.id.connect_button)

    }
}