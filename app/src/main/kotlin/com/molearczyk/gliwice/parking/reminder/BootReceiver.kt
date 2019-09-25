package com.molearczyk.gliwice.parking.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmCalculator
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmIntentCreator
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmStorageModel
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmStorageRepository
import io.realm.Realm

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val alarmHandler = AlarmIntentCreator(context, AlarmCalculator())
            AlarmStorageRepository(Realm.getDefaultInstance()).fetchAll()
                    .map(AlarmStorageModel::dayOfWeek)
                    .forEach {
                        Log.d("BootReceiver", "Restoring alarm for ${it}.")
                        alarmHandler.enableAlarm(it)
                    }
        }
    }

}