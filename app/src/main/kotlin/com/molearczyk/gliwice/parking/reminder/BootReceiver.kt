package com.molearczyk.gliwice.parking.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import io.realm.Realm
import io.realm.kotlin.where

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val alarmHandler = WeekdayAlarmHandler(context)
        Realm.getDefaultInstance().where<AlarmStorageModel>().findAll()
                .forEach {
                    Log.d("BootReceiver", "Restoring alarm for ${it}.")
                    alarmHandler.enableAlarm(it.dayOfWeek)
                }
    }

}