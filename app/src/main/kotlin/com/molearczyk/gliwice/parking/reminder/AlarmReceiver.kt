package com.molearczyk.gliwice.parking.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmStorageRepository
import io.realm.Realm
import org.threeten.bp.LocalDate

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtility.createNotificationIntent(context)
        AlarmStorageRepository(Realm.getDefaultInstance()).clearPersistedAlarm(LocalDate.now().dayOfWeek)
    }

}