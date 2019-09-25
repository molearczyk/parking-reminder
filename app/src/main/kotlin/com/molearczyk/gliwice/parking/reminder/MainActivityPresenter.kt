package com.molearczyk.gliwice.parking.reminder

import android.content.Context
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmCalculator
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmIntentCreator
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmStorageModel
import com.molearczyk.gliwice.parking.reminder.alarms.AlarmStorageRepository
import io.realm.Realm
import org.threeten.bp.DayOfWeek

class MainActivityPresenter(private val view: MainActivityView, context: Context) {

    private val alarmStorage = AlarmStorageRepository(Realm.getDefaultInstance())
    private val intentCreator by lazy { AlarmIntentCreator(context, AlarmCalculator()) }

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

}