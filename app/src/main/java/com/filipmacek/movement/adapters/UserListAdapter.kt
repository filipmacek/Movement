package com.filipmacek.movement.adapters


import android.content.Context
import android.graphics.Color
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.ListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil

import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.filipmacek.movement.R
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.data.users.UserRepository
import org.koin.core.KoinComponent
import org.koin.core.inject


class UserListAdapter (private var users: List<User>):ListAdapter<User,UserListAdapter.ViewHolder>(UserItemDiffCallback()),KoinComponent{
    // Context global var
    private lateinit var context:Context

    private val userRepository:UserRepository by inject()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.user_row,parent,false)
        context = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.username.text=users[position].username
        holder.address.text=users[position].address

        holder.connect_button.setOnClickListener {
            MaterialDialog(context).show {
                input( hint = "Type your password", inputType = InputType.TYPE_CLASS_TEXT ) { dialog,text ->
                    val password = text.toString()
                    val username=users[position].username
                    //Check if password is valid
                    val user:User = userRepository.getUserByUsername(username)
                    val bundle_username = bundleOf("username" to user.username)
                    if(user.password == password) {
                        Navigation.findNavController(holder.itemView).navigate(R.id.action_user_list_to_dashboard,bundle_username)
                    }else {
                        val toast = Toast.makeText(context,"Incorrect password",Toast.LENGTH_SHORT)
                        val toastView= toast.view
                        toastView.setBackgroundColor(Color.RED)
                        toast.show()

                    }


                }
                positiveButton(R.string.login_button_text)
            }
        }



    }

    override fun getItemCount() = users.size

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val username: TextView = itemView.findViewById(R.id.username)
        val address: TextView = itemView.findViewById(R.id.address)
        val connect_button: Button = itemView.findViewById(R.id.connect_button)

    }
}

private class UserItemDiffCallback:DiffUtil.ItemCallback<User>() {
    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.username == newItem.username
    }
}