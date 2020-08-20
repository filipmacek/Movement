package com.filipmacek.movement

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.databinding.LoginFragmentBinding
import com.filipmacek.movement.viewmodels.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import java.io.FileOutputStream
import java.io.File


class LoginFragment: Fragment() {
    private lateinit var binding:LoginFragmentBinding

    private val viewModel:UserViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        // Inflate the layout for this fragment
//        val view = inflater.inflate(R.layout.login_fragment,container,false)
        binding = LoginFragmentBinding.inflate(inflater,container,false)
        binding.userListButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_login_fragment_to_user_list)
        }



        binding.loginButton.setOnClickListener {
            val username= binding.usernameField.editText?.text.toString()
            val password = binding.passwordField.editText?.text.toString()
            if( username.isBlank()) {
                binding.usernameField.error = "Please type your username"
            }
            if( password.isBlank()) {
                binding.passwordField.error = "Please type your password"
            }

            checkUserAndPassword(username,password)


        }


        return binding.root

    }

    override fun onStart() {
        super.onStart()
        // Check if username and password exists in internal storage
        // User saved his credentials
        if ("username.txt" in context!!.fileList() && "password.txt" in context!!.fileList()){
            val savedUsername = File(context?.filesDir,"username.txt").readText()
            val savedPassword = File(context?.filesDir,"password.txt").readText()
            val user: User =viewModel.checkIfValid(savedUsername)

            if(user.password == savedPassword){
                Navigation.findNavController(binding.root).navigate(R.id.action_login_fragment_to_dashboard, bundleOf("username" to user.username))
            }


        }
    }

    fun checkUserAndPassword(username:String,password:String) {
//        if(viewModel.checkIfValid(username,password){
//            MaterialAlertDialogBuilder(context).setTitle("Account login").setMessage("You are alright!!!!!").show()
//
//        } else
//        {
//            MaterialAlertDialogBuilder(context).setTitle("Account login").setMessage("Username or password are not valid").show()
//
//        }

        val user = viewModel.checkIfValid(username)

        if(user.password == password){
            Snackbar.make(binding.root,"You are logged in.",Snackbar.LENGTH_LONG).show()

            // Check if user wants to be remembered
            if(binding.remember.isChecked){
                //username
                context?.openFileOutput("username.txt", Context.MODE_PRIVATE).use {
                    if (it != null) {
                        it.write(username.toByteArray())
                    }
                }

                //password
                context?.openFileOutput("password.txt",Context.MODE_PRIVATE).use {
                    if(it != null){
                        it.write(password.toByteArray())
                    }
                }


            }

            Navigation.findNavController(binding.root).navigate(R.id.action_login_fragment_to_dashboard)

        } else
        {
            MaterialAlertDialogBuilder(context).setTitle("Account login").setMessage("Username or password are not valid").show()

        }
    }
    override fun onPause() {
        super.onPause()
        binding.usernameField.error=""
        binding.passwordField.error=""
    }
}