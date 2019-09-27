package com.molearczyk.gliwice.parking.reminder.geofencing

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.molearczyk.gliwice.parking.reminder.GeofenceParkingReceiver

class GeofenceRepository(context: Context) {

    private val appContext = context.applicationContext
    private val geofenceClient by lazy { LocationServices.getGeofencingClient(appContext) }
    private val geofenceIntent by lazy { PendingIntent.getBroadcast(appContext, 111, Intent(appContext, GeofenceParkingReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT) }

    fun enableGeofence(successListener: (Void?) -> Unit, failureListener: (Exception?) -> Unit) {
        geofenceClient.addGeofences(GeofencingRequest.Builder().apply {
            addGeofence(Geofence.Builder()
                    .setCircularRegion(GLIWICE_CENTER_LATITUDE, GLIWICE_CENTER_LONGITUDE, ZONE_RADIUS_IN_METERS)
                    .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_DWELL)
                    .setLoiteringDelay(3 * 60 * 1000)
                    .setExpirationDuration(Geofence.NEVER_EXPIRE)
                    .setRequestId("GliwiceParkingGeofence")
                    .build())
        }.build(), geofenceIntent)
                .run {
                    addOnSuccessListener(successListener)
                    addOnFailureListener(failureListener)
                }
    }

    fun disableGeofence(successListener: (Void?) -> Unit, failureListener: (Exception?) -> Unit) {
        geofenceClient.removeGeofences(geofenceIntent).run {
            addOnSuccessListener(successListener)
            addOnFailureListener(failureListener)
        }
    }


    companion object {
        private const val GLIWICE_CENTER_LATITUDE = 50.294851
        private const val GLIWICE_CENTER_LONGITUDE = 18.667815
        private const val ZONE_RADIUS_IN_METERS = 950.0f
    }


}