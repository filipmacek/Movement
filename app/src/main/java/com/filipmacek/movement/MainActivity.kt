package com.filipmacek.movement

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.karumi.dexter.Dexter
import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import com.filipmacek.movement.data.location.Coordinate
import com.filipmacek.movement.viewmodels.MainActivityViewModel
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.android.ext.android.inject
import com.filipmacek.movement.BuildConfig

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private val viewModel:MainActivityViewModel by inject()

    val x = Coordinate("12:12:04 1.1.2019",45.34232,43.2321,1)
    val y = Coordinate("12:14:08 1.1.2019",45.34232,43.2321,1)
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