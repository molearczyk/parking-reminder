package com.molearczyk.gliwice.parking.reminder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_main.*
import org.threeten.bp.DayOfWeek


class MainActivity : AppCompatActivity() {

    private val dayCheckbox by lazy {
        arrayOf(day1Checkbox, day2Checkbox, day3Checkbox, day4Checkbox, day5Checkbox)
    }

    private val alarmHandler by lazy { WeekdayAlarmHandler(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Realm.getDefaultInstance().where<AlarmStorageModel>().findAll()
                .forEach {
                    dayCheckbox[it.dayOfWeek.ordinal].isChecked = true
                }
        dayCheckbox.forEachIndexed { index, checkbox ->
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    alarmHandler.enableAlarm(DayOfWeek.values()[index])
                    persistAlarm(DayOfWeek.values()[index])
                } else {
                    alarmHandler.cancelAlarm(DayOfWeek.values()[index])
                    clearPersistedAlarm(DayOfWeek.values()[index])
                }
            }
        }
    }

    private fun persistAlarm(alarmDay: DayOfWeek) {
        val realm = Realm.getDefaultInstance()
        realm.executeTransaction {
            it.insert(AlarmStorageModel(alarmDay))
        }
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

