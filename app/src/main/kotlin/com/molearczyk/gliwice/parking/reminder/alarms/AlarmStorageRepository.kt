package com.molearczyk.gliwice.parking.reminder.alarms

import io.realm.Realm
import io.realm.RealmResults
import io.realm.kotlin.where
import org.threeten.bp.DayOfWeek

class AlarmStorageRepository(private val realm: Realm) {

    fun persistAlarm(alarmDay: DayOfWeek) {
        realm.executeTransaction {
            it.insert(AlarmStorageModel(alarmDay))
        }
    }

    fun clearPersistedAlarm(alarmDay: DayOfWeek) {
        val realmResults = realm.where<AlarmStorageModel>().equalTo("_dayOfWeek", alarmDay.value)
                .findAll()
        realm.executeTransaction {
            realmResults.deleteFirstFromRealm()
        }
    }

    fun fetchAll(): RealmResults<AlarmStorageModel> {
        return realm.where<AlarmStorageModel>().findAll()
    }

}