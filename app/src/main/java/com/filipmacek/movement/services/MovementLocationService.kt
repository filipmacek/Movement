package com.filipmacek.movement.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.filipmacek.movement.MainActivity
import com.filipmacek.movement.R
import com.filipmacek.movement.data.location.LocationRepository
import org.koin.core.KoinComponent
import org.koin.core.inject


class MovementLocationService : Service(),KoinComponent{

    private val locationRepository:LocationRepository by inject()

    private var configurationChange = false

    private var serviceRunningInForeground = false


    private val localBinder = LocalBinder()

    private lateinit var notificationManager: NotificationManager

    // Main class for receiving location updates
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // Requirements for location updates - how often, the priority etc
    private lateinit var locationRequest:LocationRequest

    // Called when FusedLocationProviderClient has a new location
    private lateinit var locationCallback: LocationCallback

    private var currentLocation:Location? = null

    override fun onCreate() {
        Log.d(TAG,"onCreate()")

        // Init Notification Manager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 1.1 Init location provider
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        //1.2 Create a location request
        locationRequest = LocationRequest()
                .setMaxWaitTime(4000)
                .setInterval(2000)
                .setFastestInterval(1000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)


        //1.3 Init the LocationCallback
        locationCallback =object: LocationCallback(){
            override fun onLocationResult(locationResult:  LocationResult?) {
                super.onLocationResult(locationResult)
                if(locationResult?.lastLocation != null){
                    currentLocation = locationResult.lastLocation
                    Log.i(TAG,currentLocation.toString())

                    // Inserting location data into database
                    locationRepository.insertLocation(locationResult.lastLocation)


                    // Notify our Main Activity that a new location was added
                    val intent = Intent(ACTION_LOCATION_BROADCAST)
                    intent.putExtra(EXTRA_LOCATION,currentLocation)
                    LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)

                    // Update notification content if this service is running as a foreground service
                    if(serviceRunningInForeground){
                        notificationManager.notify(
                            NOTIFICATION_ID,
                            generateNotification(currentLocation)
                        )
                    }

                }

            }
        }
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"onStartCommand")

        val cancelLocationTrackingFromNotification = intent?.getBooleanExtra(
            EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION,false)

        if(cancelLocationTrackingFromNotification!!){
            unsubscribeToLocationUpdates()
            stopSelf()
        }

        // Tells the system not to recreate the service after it's been killed
        return START_NOT_STICKY

    }


    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG,"onBind()")

        // MainActivity (client) returns to the foreground and rebinds to service, so the service
        // can become a background service

        stopForeground(true)
        serviceRunningInForeground = false
        configurationChange = false
        return localBinder

    }

    override fun onRebind(intent: Intent?) {
        Log.d(TAG,"onRebind()")

        // MainActivity (client) returns to the foreground and rebinds to service, so the service
        // can become a background services.

        stopForeground(true)
        serviceRunningInForeground=false
        configurationChange=false
        super.onRebind(intent)
    }


    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG,"onUnbind()")
        // MainActivity (client) leaves foreground, so service needs to become a foreground service
        // to maintain the 'while-in-use' label.
        // NOTE: If this method is called due to a configuration change in MainActivity,
        // we do nothing.

        if(!configurationChange && true){
            Log.d(TAG,"Start foreground service")
            val notification = generateNotification(currentLocation)
            startForeground(NOTIFICATION_ID,notification)
            serviceRunningInForeground=true
        }

        // Ensures onRebind() is called if MainActivity(client) rebinds
        return true
    }













    override fun onDestroy() {
        Log.i(TAG,"onDestroy()")
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        configurationChange = true
    }

    fun subscribeToLocationUpdates() {
        Log.i(TAG,"Subscribing to location updates")

        // Binding to this service doesn't actually trigger onStartCommand
        // The service needs to be officially started(which we do here)
        startService(Intent(applicationContext,MovementLocationService::class.java))

        try {
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,locationCallback, Looper.myLooper()
            )

        }catch (unlikely:SecurityException){
            Log.e(TAG,"Lost location permissions. $unlikely")
        }

    }

    fun unsubscribeToLocationUpdates(){
        Log.i(TAG,"Unsubscribed from location updates")

        try {
            val removeTask=fusedLocationProviderClient.removeLocationUpdates(locationCallback)
            removeTask.addOnCompleteListener{ task->
                if(task.isSuccessful){
                    Log.i(TAG,"Location Callback removed")
                    stopSelf()
                }else {
                    Log.i(TAG,"Failed to remove Location Callback ")
                }
            }

        }catch (unlikely:SecurityException){
            Log.e(TAG,"Failed to remove Location Callback")
        }
    }


    /**
     *
     *   Generate a BIG_TEXT_STYLE Notification that represents latest location
     */
    private fun generateNotification(location: Location?):Notification {
        Log.d(TAG,"generateNotification()")

        // Main steps for building BIG test style notification:
        // 1. Get data
        // 2. Create notification channel for 0+
        // 3. Build the BIG_TEXT_STYLE
        // 4. Set up Intent / Pending Intent for notification
        // 5. Build and issue the notification


        // 1. Get data
        val mainNotificationText=makeLocationString(currentLocation)
        val titleText = "Movement App"

        // Create notification channel fpr 0+ and beyond devices (26+)
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,titleText,NotificationManager.IMPORTANCE_DEFAULT)

            // Add notificationChannel to system
            notificationManager.createNotificationChannel(notificationChannel)
        }


        // 2. Build the BIG_STYLE_TEXT
        val bigTextStyle = NotificationCompat.BigTextStyle()
            .bigText(mainNotificationText)
            .setBigContentTitle(titleText)

        // 3. Set up Intent/Pending intent for notification
        val launchActivityIntent = Intent(this,MainActivity::class.java)

        val cancelIntent = Intent(this,MovementLocationService::class.java)
        cancelIntent.putExtra(EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION,true)

        val servicePendingIntent = PendingIntent.getService(this,0,cancelIntent,PendingIntent.FLAG_UPDATE_CURRENT)

        val activityPendingIntent = PendingIntent.getActivity(this,0,launchActivityIntent,0)

        // 4. Build and issue the notification
        val notificationCompatBuilder=NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)

        return notificationCompatBuilder
            .setStyle(bigTextStyle)
            .setContentTitle(titleText)
            .setContentText(mainNotificationText)
            .setOngoing(true)
            .setSmallIcon(R.drawable.notification_icon)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .addAction(0,"Launch activity",activityPendingIntent)
            .addAction(1,"Stop receiving location",servicePendingIntent)
            .build()
    }

    private fun makeLocationString(location: Location?):String{
        var location_string=""
        if(location== null){
            return "No current location"
        }else{
          location_string=location.latitude.toString()+","+location.longitude.toString()+",alt="+location.altitude.toString()
        }
        return location_string

    }


    inner class LocalBinder: Binder() {
        internal val service :MovementLocationService
            get() = this@MovementLocationService
    }

    companion object {
        private const val TAG="MovementLocationService"

        private const val PACKAGE_NAME = "com.filipmacek.movement"

        internal const val ACTION_LOCATION_BROADCAST = "$PACKAGE_NAME.action.LOCATION_BROADCAST"

        internal const val EXTRA_LOCATION = "$PACKAGE_NAME.extra.LOCATION"

        private const val EXTRA_CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION = "$PACKAGE_NAME.extra.CANCEL_LOCATION_TRACKING_FROM_NOTIFICATION"

        private const val NOTIFICATION_ID = 1234

        private const val NOTIFICATION_CHANNEL_ID = "movement_channel_01"
    }

}