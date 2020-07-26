package com.filipmacek.movement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

class LoginFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.login_fragment,container,false)

//        view.login_button.setOnClickListener {
//            Toast.makeText(context,"Login",Toast.LENGTH_SHORT).show()
//        }
//        view.signup_button.setOnClickListener {
//            (activity as NavigationHost).navigateTo(SignUpFragment(),false)
//        }

        view.findViewById<Button>(R.id.login_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_login_fragment_to_dashboard)
        }

        view.findViewById<Button>(R.id.user_list_button).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_login_fragment_to_user_list)
        }

        return view

    }
}