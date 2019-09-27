package com.molearczyk.gliwice.parking.reminder.ui

import android.content.Context
import android.util.Log
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmCalculator
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmIntentCreator
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmStorageModel
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmStorageRepository
import com.molearczyk.gliwice.parking.reminder.geofencing.GeofenceRepository
import io.realm.Realm
import org.threeten.bp.DayOfWeek

class MainActivityPresenter(private val view: MainActivityView, context: Context) {

    private val alarmStorage = AlarmStorageRepository(Realm.getDefaultInstance())
    private val intentCreator by lazy { AlarmIntentCreator(context, AlarmCalculator()) }
    private val geofencingRepository by lazy { GeofenceRepository(context) }

    fun init() {
        alarmStorage.fetchAll()
                .map(AlarmStorageModel::dayOfWeek)
                .forEach(view::markAsEnabledFor)
    }

    fun onAlarmEnabled(day: DayOfWeek) {
        intentCreator.enableAlarm(day)
        alarmStorage.persistAlarm(day)
    }

    fun onAlarmDisabled(day: DayOfWeek) {
        intentCreator.cancelAlarm(day)
        alarmStorage.clearPersistedAlarm(day)
    }

    fun onGeofenceEnabled() {
        geofencingRepository.enableGeofence({
            Log.d("Geofence", "Enabled geofence successfully!")
        }) {
            Log.e("Geofence", "Geofence enabling encountered an error.", it)
            view.geofencingActionFailed()
        }
    }

    fun onGeofenceDisabled() {
        geofencingRepository.disableGeofence({
            Log.d("Geofence", "Disabled geofence successfully!")
        }) {
            Log.e("Geofence", "Geofence disabling encountered an error.", it)
            view.geofencingActionFailed()
        }
    }

}