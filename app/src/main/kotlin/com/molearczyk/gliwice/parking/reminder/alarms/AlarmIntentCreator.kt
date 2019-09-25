package com.molearczyk.gliwice.parking.reminder.alarms

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.molearczyk.gliwice.parking.reminder.AlarmReceiver
import org.threeten.bp.DayOfWeek
import org.threeten.bp.OffsetDateTime


class AlarmIntentCreator(context: Context, private val calculator: AlarmCalculator) {

    private val appContext: Context = context.applicationContext
    private val alarmManager: AlarmManager by lazy { appContext.getSystemService(AlarmManager::class.java) }

    fun cancelAlarm(alarmDay: DayOfWeek) {
        alarmManager.cancel(createIntent(alarmDay))
    }

    fun enableAlarm(alarmDay: DayOfWeek) {
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calculator.computeTriggerTime(OffsetDateTime.now(), alarmDay).asMillis, createIntent(alarmDay))
    }

    private fun createIntent(alarmDay: DayOfWeek): PendingIntent {
        val dayIndex = alarmDay.value
        return PendingIntent.getBroadcast(appContext, 999 - dayIndex, Intent(appContext, AlarmReceiver::class.java).apply {
            type = "alarm/day$dayIndex"
        }, PendingIntent.FLAG_CANCEL_CURRENT)
    }


    private val OffsetDateTime.asMillis: Long
        get() = this.toEpochSecond() * 1000

}