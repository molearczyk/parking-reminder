package com.molearczyk.gliwice.parking.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import io.realm.Realm
import io.realm.kotlin.where
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        NotificationUtility.createNotificationIntent(context)
        clearPersistedAlarm(LocalDate.now().dayOfWeek)
    }


    private fun clearPersistedAlarm(alarmDay: DayOfWeek) {
        val realm = Realm.getDefaultInstance()
        val realmResults = realm.where<AlarmStorageModel>().equalTo("_dayOfWeek", alarmDay.value)
                .findAll()
        realm.executeTransaction {
            realmResults.deleteFirstFromRealm()
        }
    }

}