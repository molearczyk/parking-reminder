package com.molearczyk.gliwice.parking.reminder

import android.app.Application

import com.jakewharton.threetenabp.AndroidThreeTen

import io.realm.Realm
import io.realm.RealmConfiguration

class ParkingReminderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        AndroidThreeTen.init(this)
        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .name("parkingReminder.realm")
                .build()
        Realm.setDefaultConfiguration(config)
    }
}
