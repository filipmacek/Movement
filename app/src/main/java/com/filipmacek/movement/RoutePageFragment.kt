package com.filipmacek.movement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.filipmacek.movement.adapters.RouteListAdapter
import com.filipmacek.movement.adapters.UserListAdapter
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.databinding.RoutePageFragmentBinding
import com.filipmacek.movement.viewmodels.RouteViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.koin.android.ext.android.inject

class RoutePageFragment(private val username:String) :Fragment(){
    private val compositeDisposable:CompositeDisposable = CompositeDisposable()

    private lateinit var binding: RoutePageFragmentBinding

    private val viewModel:RouteViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RoutePageFragmentBinding.inflate(inflater,container,false)
        binding.routeList.layoutManager = LinearLayoutManager(context)

        viewModel.routes.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({routes->
                    val adapter =RouteListAdapter(routes,username)
                    binding.routeList.adapter = adapter

                },{error ->
                    Log.e(TAG,error.toString())

                }).addTo(compositeDisposable)




        return binding.root


    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    companion object{
        const val TAG="RoutePageFragment"
    }
}