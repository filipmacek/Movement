package com.filipmacek.movement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.karumi.dexter.Dexter
import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.location.Location
import android.os.IBinder
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import com.filipmacek.movement.data.location.LocationRepository
import com.filipmacek.movement.data.users.User
import com.filipmacek.movement.services.MovementLocationService
import com.filipmacek.movement.viewmodels.MainActivityViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.SettingsClient
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.android.ext.android.inject
import java.io.File


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val viewModel:MainActivityViewModel by inject()


    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Check permissions
        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                        if (report?.areAllPermissionsGranted() == true) {
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permissions: MutableList<PermissionRequest>?,
                            token: PermissionToken?
                    ) {}
                }).check()

    }





    companion object {
        private const val TAG="MainActivity"
    }
}