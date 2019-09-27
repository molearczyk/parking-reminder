package com.molearczyk.gliwice.parking.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent

class GeofenceParkingReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {

            Log.e("GeofenceReceiver", "Geofencing event failed with ${geofencingEvent.errorCode}")
            return
        }

        if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL || geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            NotificationUtility.createNotificationIntent(context)
        }
    }

}